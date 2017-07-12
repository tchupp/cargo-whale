import {ChangeDetectorRef, Component, OnDestroy, OnInit} from "@angular/core";
import {ActivatedRoute, Params} from "@angular/router";
import {Observable, Subscription} from "rxjs/Rx";
import {ContainerIndex, ContainerIndexEmbedded, ContainerIndexLinks, StateMetadata} from "./container-index.model";
import {ContainerIndexService} from "./container-index.service";
import {Container} from "../container.model";
import {EventsService} from "../../../../shared/events/events.service";

@Component({
    selector: 'cw-container-list',
    templateUrl: 'app/pages/containers/components/index/container-index.html'
})
export class ContainerIndexComponent implements OnInit, OnDestroy {

    private containers: Container[];
    private stateMetadata: StateMetadata;
    private subscription: Subscription;
    private loading: boolean = true;

    constructor(private route: ActivatedRoute,
                private containerIndexService: ContainerIndexService,
                private eventsService: EventsService,
                private changeDetector: ChangeDetectorRef) {
    }

    ngOnInit(): void {
        const links: ContainerIndexLinks = this.route.snapshot.data['containerIndexLinks'];

        this.subscription = Observable.combineLatest(
            this.route.queryParams,
            this.eventsService.followContainerEvents()
                .startWith({})
        )
            .map(latest => latest[0])
            .do(() => this.loading = true)
            .map((query: Params) => query['state'] || 'all')
            .mergeMap((state: string) => this.containerIndexService.follow(links, state))
            .do(() => this.loading = false)
            .subscribe((containerIndex: ContainerIndex) => {
                this.stateMetadata = containerIndex.stateMetadata;
                this.containers = (containerIndex._embedded || new ContainerIndexEmbedded()).containers;

                this.changeDetector.detectChanges();
            }, (error: any) => {
                console.error(error);
            });
    }

    ngOnDestroy(): void {
        this.subscription.unsubscribe();
    }
}
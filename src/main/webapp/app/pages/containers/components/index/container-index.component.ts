import {Component, OnInit, OnDestroy} from "@angular/core";
import {ActivatedRoute, Params} from "@angular/router";
import {Subscription} from "rxjs/Rx";
import {ContainerIndex, StateMetadata, ContainerIndexEmbedded, ContainerIndexLinks} from "./container-index.model";
import {ContainerIndexService} from "./container-index.service";
import {Container} from "../container.model";

@Component({
    selector: 'cw-container-list',
    templateUrl: 'app/pages/containers/components/index/container-index.html'
})
export class ContainerIndexComponent implements OnInit, OnDestroy {

    private containers: Container[];
    private stateMetadata: StateMetadata;
    private subscription: Subscription;
    private loading: boolean = true;

    constructor(private route: ActivatedRoute, private containerIndexService: ContainerIndexService) {
    }

    ngOnInit(): void {
        this.containerIndexService.getContainerIndexLinks().subscribe((links: ContainerIndexLinks) => {
            this.subscription = this.route.queryParams.subscribe((query: Params) => {
                const state = query['state'] || 'all';

                this.getContainerIndex(links, state);
            });
        });
    }

    ngOnDestroy(): void {
        this.subscription.unsubscribe();
    }

    private getContainerIndex(links: ContainerIndexLinks, state: string) {
        this.loading = true;

        this.containerIndexService.follow(links, state).subscribe((containerIndex: ContainerIndex) => {
            const embedded = containerIndex._embedded || new ContainerIndexEmbedded();

            this.containers = embedded.containers;
            this.stateMetadata = containerIndex.stateMetadata;
            this.loading = false;
        }, () => {
            this.loading = false;
        });
    }
}
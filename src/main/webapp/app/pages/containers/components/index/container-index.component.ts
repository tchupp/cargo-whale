import {Component, OnInit, OnDestroy} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {Observable, Subscription} from "rxjs/Rx";
import {ContainerIndex, StateMetadata} from "./container-index.model";
import {Container} from "../container.model";
import {ContainersService} from "../../containers.service";

@Component({
    selector: 'cw-container-list',
    templateUrl: 'app/pages/containers/components/index/container-index.html'
})
export class ContainerIndexComponent implements OnInit, OnDestroy {

    private containers: Container[];
    private stateMetadata: StateMetadata;
    private subscription: Subscription;
    private loading: boolean = true;

    constructor(private route: ActivatedRoute, private containerService: ContainersService) {
    }

    ngOnInit(): void {
        this.subscription = this.route.queryParams.subscribe(query => {
            this.getContainerIndex(query['state']);
        });
    }

    ngOnDestroy(): void {
        this.subscription.unsubscribe();
    }

    private getContainerIndex(state: string = null) {
        this.loading = true;

        let response: Observable<ContainerIndex>;
        if (!state || state === 'all') {
            response = this.containerService.getContainerIndex();
        } else {
            response = this.containerService.getFilteredContainerIndex(state);
        }
        response.subscribe(containerIndex => {
            const embedded = containerIndex._embedded || {containers: []};

            this.containers = embedded.containers;
            this.stateMetadata = containerIndex.stateMetadata;
            this.loading = false;
        });
    }
}
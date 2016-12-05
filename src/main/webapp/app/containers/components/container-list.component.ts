import {Component, OnInit} from "@angular/core";

import {ContainerService} from "./container.service";
import {ContainerSummary} from "./container-summary.model";
import {ContainerIndex} from "./container-index.model";
import {StateMetadata} from "./state-metadata.model";

@Component({
    selector: 'cw-container-list',
    templateUrl: 'app/containers/components/container-list.html',
    providers: [
        ContainerService
    ]
})
export class ContainerListComponent implements OnInit {

    private containers: ContainerSummary[];
    private stateMetadata: StateMetadata;
    private stateMetadataKeys: string[];

    constructor(private containerService: ContainerService) {
    }

    ngOnInit(): void {
        this.containerService.getAllContainers<ContainerIndex>().subscribe(containerIndex => {
            this.containers = containerIndex.containers;
            this.stateMetadata = containerIndex.stateMetadata;
            this.stateMetadataKeys = Object.keys(containerIndex.stateMetadata);
        });
    }
}
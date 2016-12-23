import {Component, OnInit} from "@angular/core";

import {ContainerService} from "./container.service";
import {ContainerIndexItem} from "./container-index-item.model";
import {ContainerIndex} from "./container-index.model";
import {StateMetadata} from "./state-metadata.model";

@Component({
    selector: 'cw-container-list',
    templateUrl: 'app/pages/containers/components/container-index.html',
    providers: [
        ContainerService
    ]
})
export class ContainerIndexComponent implements OnInit {

    private containers: ContainerIndexItem[];
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
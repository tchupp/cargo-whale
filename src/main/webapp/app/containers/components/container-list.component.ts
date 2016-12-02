import {Component, OnInit} from "@angular/core";

import {ContainerService} from "./container.service";
import {ContainerSummary} from "./container-summary.model";

@Component({
    selector: 'cw-container-list',
    templateUrl: 'app/containers/components/container-list.html',
    providers: [
        ContainerService
    ]
})
export class ContainerListComponent implements OnInit {

    private containerList: ContainerSummary[];

    constructor(private containerService: ContainerService) {
    }

    ngOnInit(): void {
        this.containerService.getAllContainers().subscribe(allContainers => {
            this.containerList = allContainers;
        });
    }
}
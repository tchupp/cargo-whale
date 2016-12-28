import {Component, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {ContainersService} from "../../containers.service";
import {ContainerDetails} from "./container-details.model";

@Component({
    selector: 'cw-container-details',
    templateUrl: 'app/pages/containers/components/details/container-details.html'
})
export class ContainerDetailsComponent implements OnInit {

    private details: ContainerDetails;

    constructor(private activatedRoute: ActivatedRoute, private containerService: ContainersService) {
    }

    ngOnInit(): void {
        let id: string = this.activatedRoute.snapshot.params['id'];
        this.containerService.getContainerDetails<ContainerDetails>(id).subscribe(details => {
            this.details = details;
        });
    }
}
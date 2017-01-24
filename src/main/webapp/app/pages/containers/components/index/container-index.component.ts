import {Component, OnInit} from "@angular/core";
import {Router} from "@angular/router";
import {ContainersService} from "../../containers.service";
import {ContainerIndex, StateMetadata} from "./container-index.model";
import {Container} from "../container.model";

@Component({
    selector: 'cw-container-list',
    templateUrl: 'app/pages/containers/components/index/container-index.html'
})
export class ContainerIndexComponent implements OnInit {

    private containers: Container[];
    private stateMetadata: StateMetadata;

    constructor(private router: Router, private containerService: ContainersService) {
    }

    ngOnInit(): void {
        this.containerService.getContainerIndex<ContainerIndex>().subscribe(containerIndex => {
            this.containers = containerIndex._embedded.containers;
            this.stateMetadata = containerIndex.stateMetadata;
        });
    }

    onClickContainer(container: Container): void {
        this.router.navigate(['/containers', container.id]);
    }
}
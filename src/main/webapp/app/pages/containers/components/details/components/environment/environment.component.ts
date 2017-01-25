import {Component, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {ContainerConfig} from "../../../container.model";

@Component({
    selector: 'cw-container-environment',
    template: `<div *ngFor="let envVar of environment; let last = last">
                   <cw-info-item [name]="envVar | split : '=' : 0" [divider]="!last">
                       <span class="value">{{envVar | split : '=' : 1}}</span>
                   </cw-info-item>
               </div>`
})
export class ContainerEnvironmentComponent implements OnInit {

    private environment: string[];

    constructor(private route: ActivatedRoute) {
    }

    ngOnInit(): void {
        const config: ContainerConfig = this.route.snapshot.parent.data['container'].config;
        this.environment = config.env;
    }
}
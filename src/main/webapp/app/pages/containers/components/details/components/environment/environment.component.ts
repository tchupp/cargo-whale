import {Component, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";

@Component({
    selector: 'cw-container-environment',
    template: `<div *ngFor="let envVar of environment; let i = index">
                   <cw-info-item [name]="envVar | split : '=' : 0"
                                 [divider]="!(i === environment.length - 1)">
                       <span class="value">{{envVar | split : '=' : 1}}</span>
                   </cw-info-item>
               </div>`
})
export class ContainerEnvironmentComponent implements OnInit {

    private environment: string[];

    constructor(private route: ActivatedRoute) {
    }

    ngOnInit(): void {
        this.environment = this.route.snapshot.parent.data['container'].config.environment;
    }
}
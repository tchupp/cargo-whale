import {Component, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {ContainerConfig} from "../../../container.model";

@Component({
    selector: 'cw-container-config',
    template: `<div>
                   <cw-info-item [name]="'Std In'">
                       <span class="value">{{config.attachStdin}}</span>
                   </cw-info-item>
                   <cw-info-item [name]="'Std Out'">
                       <span class="value">{{config.attachStdout}}</span>
                   </cw-info-item>
                   <cw-info-item [name]="'Std Err'">
                       <span class="value">{{config.attachStderr}}</span>
                   </cw-info-item>
                   <pre>
                       {{config | json}}
                   </pre>
               </div>`
})
export class ContainerConfigComponent implements OnInit {

    private config: ContainerConfig;

    constructor(private route: ActivatedRoute) {
    }

    ngOnInit(): void {
        this.config = this.route.snapshot.parent.data['container'].config;
    }
}
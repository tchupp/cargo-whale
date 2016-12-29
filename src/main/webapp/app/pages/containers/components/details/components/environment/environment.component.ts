import {Component, OnInit, Input} from "@angular/core";

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

    @Input() environment: string[];

    ngOnInit(): void {
    }
}
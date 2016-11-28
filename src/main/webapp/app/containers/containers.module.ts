import {NgModule, CUSTOM_ELEMENTS_SCHEMA} from "@angular/core";
import {CommonModule} from "@angular/common";

import {ContainersComponent} from "./containers.component";
import {ContainerListComponent} from "./components/container-list.component";

@NgModule({
    imports: [
        CommonModule
    ],
    declarations: [
        ContainersComponent,
        ContainerListComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ContainersModule {
}
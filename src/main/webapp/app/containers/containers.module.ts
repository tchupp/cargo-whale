import {NgModule, CUSTOM_ELEMENTS_SCHEMA} from "@angular/core";
import {CommonModule} from "@angular/common";

import {ContainersComponent} from "./containers.component";
import {ContainerListComponent} from "./components/container-list.component";
import {containersRoutes} from "./containers.routes";

@NgModule({
    imports: [
        CommonModule,
        containersRoutes
    ],
    declarations: [
        ContainersComponent,
        ContainerListComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ContainersModule {
}
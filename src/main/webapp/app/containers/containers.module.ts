import {NgModule, CUSTOM_ELEMENTS_SCHEMA} from "@angular/core";
import {CommonModule} from "@angular/common";

import {ContainersComponent} from "./components/container-detail.component";
import {ContainerListComponent} from "./components/container-list.component";
import {containersRoutes} from "./containers.routes";
import {LayoutsModule} from "../layouts/layouts.module";

@NgModule({
    imports: [
        CommonModule,
        LayoutsModule,
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
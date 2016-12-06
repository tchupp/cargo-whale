import {NgModule, CUSTOM_ELEMENTS_SCHEMA} from "@angular/core";
import {CommonModule} from "@angular/common";

import {ContainersComponent} from "./components/container-detail.component";
import {ContainerListComponent} from "./components/container-list.component";
import {containersRoutes} from "./containers.routes";
import {LayoutsModule} from "../layouts/layouts.module";
import {SharedModule} from "../shared/shared.module";

@NgModule({
    imports: [
        CommonModule,
        LayoutsModule,
        SharedModule,
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
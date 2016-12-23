import {NgModule, CUSTOM_ELEMENTS_SCHEMA} from "@angular/core";
import {CommonModule} from "@angular/common";

import {ContainersComponent} from "./containers.component";
import {ContainerDetailsComponent} from "./components/container-detail.component";
import {ContainerIndexComponent} from "./components/container-index.component";
import {containersRoutes} from "./containers.routes";
import {LayoutsModule} from "../../layouts/layouts.module";
import {SharedModule} from "../../shared/shared.module";

@NgModule({
    imports: [
        CommonModule,
        LayoutsModule,
        SharedModule,
        containersRoutes
    ],
    declarations: [
        ContainersComponent,
        ContainerDetailsComponent,
        ContainerIndexComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ContainersModule {
}
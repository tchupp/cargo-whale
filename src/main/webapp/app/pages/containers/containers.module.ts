import {NgModule, CUSTOM_ELEMENTS_SCHEMA} from "@angular/core";
import {CommonModule} from "@angular/common";

import {ContainersComponent} from "./containers.component";
import {ContainerDetailsComponent} from "./components/details/container-details.component";
import {ContainerIndexComponent} from "./components/index/container-index.component";
import {containersRoutes} from "./containers.routes";
import {ContainersService} from "./containers.service";
import {LayoutsModule} from "../../shared/layouts";
import {PipesModule} from "../../shared/pipes";
import {InfoItemComponent} from "./components/info-item/info-item.component";

@NgModule({
    imports: [
        CommonModule,
        LayoutsModule,
        PipesModule,
        containersRoutes
    ],
    declarations: [
        ContainersComponent,
        ContainerDetailsComponent,
        ContainerIndexComponent,
        InfoItemComponent
    ],
    providers: [ContainersService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ContainersModule {
}
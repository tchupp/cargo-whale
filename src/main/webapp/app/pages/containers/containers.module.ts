import {NgModule, CUSTOM_ELEMENTS_SCHEMA} from "@angular/core";
import {CommonModule} from "@angular/common";
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";

import {ContainersComponent} from "./containers.component";
import {ContainerDetailsComponent} from "./components/details/container-details.component";
import {ContainerIndexComponent} from "./components/index/container-index.component";
import {ContainerEnvironmentComponent} from "./components/details/components/environment/environment.component";
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
        NgbModule,
        containersRoutes
    ],
    declarations: [
        ContainersComponent,
        ContainerDetailsComponent,
        ContainerIndexComponent,
        InfoItemComponent,
        ContainerEnvironmentComponent
    ],
    providers: [ContainersService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ContainersModule {
}
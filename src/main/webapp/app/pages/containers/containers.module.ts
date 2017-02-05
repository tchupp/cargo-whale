import {NgModule, CUSTOM_ELEMENTS_SCHEMA} from "@angular/core";
import {CommonModule} from "@angular/common";
import {RouterModule} from "@angular/router";

import {containersRoute} from "./containers.routes";
import {ContainersComponent} from "./containers.component";
import {ContainersService} from "./containers.service";
import {ContainerDetailsComponent} from "./components/details/container-details.component";
import {ContainerDetailsResolver} from "./components/details/container-details.resolver";
import {ContainerConfigComponent} from "./components/details/components/config/container-config.component";
import {ContainerEnvironmentComponent} from "./components/details/components/environment/environment.component";
import {ContainerNetworkComponent} from "./components/details/components/networks/networks.component";
import {ContainerIndexComponent} from "./components/index/container-index.component";
import {InfoItemComponent} from "./components/info-item/info-item.component";
import {LayoutsModule} from "../../shared/layouts";
import {PipesModule} from "../../shared/pipes";

@NgModule({
    imports: [
        CommonModule,
        LayoutsModule,
        PipesModule,
        RouterModule.forChild([containersRoute])
    ],
    declarations: [
        ContainersComponent,
        ContainerDetailsComponent,
        ContainerIndexComponent,
        InfoItemComponent,
        ContainerConfigComponent,
        ContainerEnvironmentComponent,
        ContainerNetworkComponent
    ],
    providers: [
        ContainersService,
        ContainerDetailsResolver
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export default class ContainersModule {
}
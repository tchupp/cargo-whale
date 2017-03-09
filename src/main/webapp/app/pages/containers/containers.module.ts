import {NgModule, CUSTOM_ELEMENTS_SCHEMA} from "@angular/core";
import {CommonModule} from "@angular/common";
import {RouterModule} from "@angular/router";

import {containersRoute} from "./containers.routes";
import {ContainersComponent} from "./containers.component";
import {ContainerDetailsService} from "./components/details/container-details.service";
import {ContainerDetailsComponent} from "./components/details/container-details.component";
import {ContainerDetailsResolver} from "./components/details/container-details.resolver";
import {ContainerConfigComponent} from "./components/details/components/config/container-config.component";
import {ContainerEnvironmentComponent} from "./components/details/components/environment/environment.component";
import {ContainerLogsComponent} from "./components/details/components/logs/container-logs.component";
import {ContainerLogsService} from "./components/details/components/logs/container-logs.service";
import {ContainerNetworkComponent} from "./components/details/components/networks/networks.component";
import {ContainerIndexComponent} from "./components/index/container-index.component";
import {ContainerIndexService} from "./components/index/container-index.service";
import {InfoItemComponent} from "./components/info-item/info-item.component";
import {LayoutsModule} from "../../shared/layouts";
import {PipesModule} from "../../shared/pipes";
import {ContainerIndexResolver} from "./components/index/container-index.resolver";

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
        ContainerLogsComponent,
        ContainerConfigComponent,
        ContainerEnvironmentComponent,
        ContainerNetworkComponent
    ],
    providers: [
        ContainerDetailsService,
        ContainerDetailsResolver,
        ContainerLogsService,
        ContainerIndexService,
        ContainerIndexResolver
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export default class ContainersModule {
}
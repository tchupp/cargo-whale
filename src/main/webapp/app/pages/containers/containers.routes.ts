import {Route} from "@angular/router";

import {ContainersComponent} from "./containers.component";
import {ContainerIndexComponent} from "./components/index/container-index.component";
import {ContainerIndexResolver} from "./components/index/container-index.resolver";
import {ContainerDetailsComponent} from "./components/details/container-details.component";
import {ContainerConfigComponent} from "./components/details/components/config/container-config.component";
import {ContainerEnvironmentComponent} from "./components/details/components/environment/environment.component";
import {ContainerLogsComponent} from "./components/details/components/logs/container-logs.component";
import {ContainerNetworkComponent} from "./components/details/components/networks/networks.component";
import {ContainerDetailsResolver} from "./components/details/container-details.resolver";

export const containersRoute: Route = {
    path: '',
    component: ContainersComponent,
    children: [
        {
            path: '',
            component: ContainerIndexComponent,
            resolve: {
                containerIndexLinks: ContainerIndexResolver
            }
        },
        {
            path: ':id',
            component: ContainerDetailsComponent,
            resolve: {
                container: ContainerDetailsResolver
            },
            children: [
                {path: '', redirectTo: 'environment', pathMatch: 'full'},
                {path: 'config', component: ContainerConfigComponent},
                {path: 'environment', component: ContainerEnvironmentComponent},
                {path: 'logs', component: ContainerLogsComponent},
                {path: 'network', component: ContainerNetworkComponent}
            ]
        }
    ]
};

import {Route} from "@angular/router";

import {ContainersComponent} from "./containers.component";
import {ContainerIndexComponent} from "./components/index/container-index.component";
import {ContainerDetailsComponent} from "./components/details/container-details.component";
import {ContainerConfigComponent} from "./components/details/components/config/container-config.component";
import {ContainerEnvironmentComponent} from "./components/details/components/environment/environment.component";
import {ContainerNetworkComponent} from "./components/details/components/networks/networks.component";
import {ContainerDetailsResolver} from "./components/details/container-details.resolver";

export const containersRoute: Route = {
    path: 'containers',
    component: ContainersComponent,
    children: [
        {
            path: '',
            component: ContainerIndexComponent
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
                {path: 'network', component: ContainerNetworkComponent}
            ]
        }
    ]
};

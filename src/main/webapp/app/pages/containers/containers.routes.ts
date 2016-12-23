import {Routes, RouterModule} from "@angular/router";

import {ContainersComponent} from "./containers.component";
import {ContainerDetailsComponent} from "./components/container-detail.component";
import {ContainerIndexComponent} from "./components/container-index.component";

const routes: Routes = [
    {
        path: 'containers',
        component: ContainersComponent,
        children: [
            {
                path: '',
                component: ContainerIndexComponent
            },
            {
                path: ':id',
                component: ContainerDetailsComponent
            }
        ]
    }
];

export const containersRoutes = RouterModule.forChild(routes);

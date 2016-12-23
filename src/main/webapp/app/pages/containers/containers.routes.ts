import {Routes, RouterModule} from "@angular/router";

import {ContainerIndexComponent} from "./components/container-index.component";
import {ContainerDetailsComponent} from "./components/container-detail.component";

const routes: Routes = [
    {
        path: 'containers',
        component: ContainerIndexComponent
    },
    {
        path: 'container/:id',
        component: ContainerDetailsComponent
    }
];

export const containersRoutes = RouterModule.forRoot(routes);

import {Routes, RouterModule} from "@angular/router";

import {ContainerListComponent} from "./components/container-list.component";

const routes: Routes = [
    {
        path: 'containers',
        component: ContainerListComponent
    }
];

export const containersRoutes = RouterModule.forRoot(routes);

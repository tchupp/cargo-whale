import {Routes, RouterModule} from "@angular/router";

import {ContainersComponent} from "./containers.component";

const routes: Routes = [
    {
        path: 'containers',
        component: ContainersComponent
    }
];

export const containersRoutes = RouterModule.forRoot(routes);

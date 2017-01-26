import {Routes, RouterModule} from "@angular/router";
import {PagesComponent} from "./pages.component";
import {containersRoute} from "./containers/containers.routes";
import {dashboardRoute} from "./dashboard/dashboard.routes";

const routes: Routes = [
    {
        path: 'pages',
        component: PagesComponent,
        children: [
            containersRoute,
            dashboardRoute,
            {path: '', redirectTo: 'dashboard', pathMatch: 'full'},
        ]
    },
    {
        path: '',
        redirectTo: 'pages',
        pathMatch: 'full',
    }
];

export const pagesRoutes = RouterModule.forRoot(routes, {useHash: true, enableTracing: true});
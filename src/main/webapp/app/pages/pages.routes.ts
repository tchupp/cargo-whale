import {Routes, RouterModule} from "@angular/router";
import {PagesComponent} from "./pages.component";

const routes: Routes = [
    {
        path: 'login',
        loadChildren: 'app/pages/login/login.module'
    },
    {
        path: '',
        redirectTo: 'login',
        pathMatch: 'full'
    },
    {
        path: '',
        component: PagesComponent,
        children: [
            {path: 'containers', loadChildren: 'app/pages/containers/containers.module'},
            {path: 'dashboard', loadChildren: 'app/pages/dashboard/dashboard.module'},
        ]
    }
];

export const pagesRoutes = RouterModule.forRoot(routes, {useHash: true});
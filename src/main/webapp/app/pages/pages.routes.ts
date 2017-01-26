import {Routes, RouterModule} from "@angular/router";
import {PagesComponent} from "./pages.component";

const routes: Routes = [
    {
        path: '',
        component: PagesComponent,
        children: [
            {path: 'containers', loadChildren: 'app/pages/containers/containers.module'},
            {path: 'dashboard', loadChildren: 'app/pages/dashboard/dashboard.module'},
            {path: '', redirectTo: 'dashboard', pathMatch: 'full'},
        ]
    }
];

export const pagesRoutes = RouterModule.forRoot(routes, {useHash: true, enableTracing: true});
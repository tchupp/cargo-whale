import {Routes, RouterModule} from "@angular/router";

import {PagesComponent} from "./pages.component";

const routes: Routes = [
    {
        path: '',
        component: PagesComponent,
        children: [
            {path: '', redirectTo: '/containers', pathMatch: 'full'},
            {path: '**', redirectTo: '/containers'}
        ]
    }
];

export const pagesRoutes = RouterModule.forChild(routes);
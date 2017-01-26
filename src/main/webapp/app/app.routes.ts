import {Routes, RouterModule} from "@angular/router";

const routes: Routes = [
    {
        path: 'pages',
        loadChildren: 'pages'
    }
];

export const appRoutes = RouterModule.forRoot(routes, {useHash: true, enableTracing: true});

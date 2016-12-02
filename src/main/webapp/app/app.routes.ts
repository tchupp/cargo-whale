import {Routes, RouterModule} from "@angular/router";

const routes: Routes = [
    {path: '', redirectTo: '/containers', pathMatch: 'full'},
    {path: '**', redirectTo: '/containers'}
];

export const appRoutes = RouterModule.forRoot(routes, {useHash: true});

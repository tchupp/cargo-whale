import {NgModule, CUSTOM_ELEMENTS_SCHEMA} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {HttpModule} from "@angular/http";
import {RouterModule} from "@angular/router";

import {MainComponent, SidebarComponent, HeaderComponent, SidebarItemComponent} from "./layouts";
import {ContainersModule} from "./containers";
import {appRoutes} from "./app.routes";

@NgModule({
    imports: [
        BrowserModule,
        HttpModule,
        RouterModule,
        ContainersModule,
        appRoutes
    ],
    declarations: [
        MainComponent,
        HeaderComponent,
        SidebarComponent,
        SidebarItemComponent
    ],
    bootstrap: [MainComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CargoWhaleDockerAppModule {
}

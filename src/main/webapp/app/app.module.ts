import {NgModule, CUSTOM_ELEMENTS_SCHEMA} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {HttpModule} from "@angular/http";
import {RouterModule} from "@angular/router";

import {PagesModule} from "./pages/pages.module";
import {LayoutsModule} from "./shared/layouts";
import {appRoutes} from "./app.routes";
import {PagesComponent} from "./pages/pages.component";

@NgModule({
    imports: [
        BrowserModule,
        HttpModule,
        RouterModule,
        PagesModule,
        appRoutes
    ],
    bootstrap: [PagesComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CargoWhaleDockerAppModule {
}

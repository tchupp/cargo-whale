import {NgModule, CUSTOM_ELEMENTS_SCHEMA} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {HttpModule} from "@angular/http";
import {RouterModule} from "@angular/router";

import {PagesModule} from "./pages/pages.module";
import {LayoutsModule, MainComponent} from "./layouts";
import {appRoutes} from "./app.routes";

@NgModule({
    imports: [
        BrowserModule,
        HttpModule,
        RouterModule,
        PagesModule,
        LayoutsModule,
        appRoutes
    ],
    bootstrap: [MainComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CargoWhaleDockerAppModule {
}

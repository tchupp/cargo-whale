import {NgModule, CUSTOM_ELEMENTS_SCHEMA} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {HttpModule} from "@angular/http";

import {PagesModule} from "./pages";
import {LayoutsModule} from "./shared/layouts";
import {AppComponent} from "./app.component";
import {AuthModule} from "./shared/auth";
import {httpProvider} from "./http/http.provider";

@NgModule({
    imports: [
        BrowserModule,
        AuthModule,
        HttpModule,
        LayoutsModule,
        PagesModule
    ],
    declarations: [
        AppComponent
    ],
    providers: [
        httpProvider()
    ],
    bootstrap: [AppComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CargoWhaleAppModule {
}

import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {HttpModule} from "@angular/http";

import {PagesModule} from "./pages";
import {LayoutsModule} from "./shared/layouts";
import {AppComponent} from "./app.component";
import {AuthModule} from "./shared/auth";
import {httpProvider} from "./http/http.provider";
import {Sse} from "./sse/sse.service";

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
        httpProvider(),
        Sse
    ],
    bootstrap: [AppComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CargoWhaleAppModule {
}

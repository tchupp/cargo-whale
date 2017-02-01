import {NgModule, CUSTOM_ELEMENTS_SCHEMA} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {HttpModule} from "@angular/http";
import {Ng2Webstorage} from 'ng2-webstorage';

import {PagesModule} from "./pages/pages.module";
import {LayoutsModule} from "./shared/layouts";
import {AppComponent} from "./app.component";

@NgModule({
    imports: [
        BrowserModule,
        Ng2Webstorage.forRoot({prefix: 'cw'}),
        HttpModule,
        LayoutsModule,
        PagesModule
    ],
    declarations: [
        AppComponent
    ],
    bootstrap: [AppComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CargoWhaleAppModule {
}

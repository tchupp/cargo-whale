import {NgModule, CUSTOM_ELEMENTS_SCHEMA} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {HttpModule} from "@angular/http";

import {MainComponent, SidebarComponent, HeaderComponent, SidebarItemComponent} from "./layouts";
import {ContainersModule} from "./containers";

@NgModule({
    imports: [
        BrowserModule,
        HttpModule,
        ContainersModule
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

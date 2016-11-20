import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {HttpModule} from "@angular/http";

import {MainComponent, SidebarComponent} from "./layouts";
import {ContainerListComponent} from "./container/container-list.component";
import {ContainerService} from "./container/container.service";


@NgModule({
    imports: [
        BrowserModule,
        HttpModule
    ],
    declarations: [
        MainComponent,
        ContainerListComponent,
        SidebarComponent
    ],
    providers: [
        ContainerService
    ],
    bootstrap: [MainComponent]
})
export class CargoWhaleDockerAppModule {
}

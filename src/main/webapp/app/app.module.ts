import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {HttpModule} from "@angular/http";

import {ContainerListComponent} from "./layouts/containers/container-list.component";
import {ContainerService} from "./layouts/containers/container.service";


@NgModule({
    imports: [
        BrowserModule,
        HttpModule
    ],
    declarations: [
        ContainerListComponent
    ],
    providers: [
        ContainerService
    ],
    bootstrap: [ContainerListComponent]
})
export class CargoWhaleDockerAppModule {
}

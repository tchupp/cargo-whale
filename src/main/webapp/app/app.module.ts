import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {HttpModule} from "@angular/http";

import {ContainersComponent} from "./layouts/container/containers.component";
import {ContainerService} from "./layouts/container/container.service";


@NgModule({
    imports: [
        BrowserModule,
        HttpModule
    ],
    declarations: [
        ContainersComponent
    ],
    providers: [
        ContainerService
    ],
    bootstrap: [ContainersComponent]
})
export class CargoWhaleDockerAppModule {
}

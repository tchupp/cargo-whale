import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {HttpModule} from "@angular/http";

import {MainComponent, SidebarComponent, HeaderComponent} from "./layouts";
import {ContainerListComponent} from "./container/container-list.component";
import {ContainerService} from "./container/container.service";
import {SidebarItemComponent} from "./layouts/sidebar/sidebar-item/sidebar-item.component";


@NgModule({
    imports: [
        BrowserModule,
        HttpModule
    ],
    declarations: [
        MainComponent,
        ContainerListComponent,
        HeaderComponent,
        SidebarComponent,
        SidebarItemComponent
    ],
    providers: [
        ContainerService
    ],
    bootstrap: [MainComponent]
})
export class CargoWhaleDockerAppModule {
}

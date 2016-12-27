import {NgModule, CUSTOM_ELEMENTS_SCHEMA} from "@angular/core";
import {CommonModule} from "@angular/common";

import {PagesComponent} from "./pages.component";
import {pagesRoutes} from "./pages.routes";
import {ContainersModule} from "./containers/containers.module";
import {LayoutsModule} from "../layouts/layouts.module";

@NgModule({
    imports: [
        CommonModule,
        LayoutsModule,
        ContainersModule,
        pagesRoutes
    ],
    declarations: [PagesComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PagesModule {
}
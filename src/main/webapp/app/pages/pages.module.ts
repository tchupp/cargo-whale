import {NgModule, CUSTOM_ELEMENTS_SCHEMA} from "@angular/core";
import {CommonModule} from "@angular/common";
import {RouterModule} from "@angular/router";

import {PagesComponent} from "./pages.component";
import {pagesRoutes} from "./pages.routes";
import {LayoutsModule} from "../shared/layouts";

@NgModule({
    imports: [
        CommonModule,
        LayoutsModule,
        pagesRoutes
    ],
    declarations: [PagesComponent],
    exports: [RouterModule],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PagesModule {
}
import {NgModule, CUSTOM_ELEMENTS_SCHEMA} from "@angular/core";
import {CommonModule} from "@angular/common";
import {RouterModule} from "@angular/router";

import {LayoutsModule} from "../../shared/layouts";
import {PipesModule} from "../../shared/pipes";
import {DashboardComponent} from "./dashboard.component";
import {dashboardRoute} from "./dashboard.routes";

@NgModule({
    imports: [
        CommonModule,
        PipesModule,
        RouterModule.forChild([dashboardRoute])
    ],
    declarations: [
        DashboardComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export default class DashboardModule {
}
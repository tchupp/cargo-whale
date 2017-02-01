import {NgModule, CUSTOM_ELEMENTS_SCHEMA} from "@angular/core";
import {CommonModule} from "@angular/common";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {RouterModule} from "@angular/router";

import {LayoutsModule} from "../../shared/layouts";
import {PipesModule} from "../../shared/pipes";
import {LoginComponent} from "./login.component";
import {loginRoute} from "./login.routes";
import {LoginService} from "./login.service";

@NgModule({
    imports: [
        CommonModule,
        ReactiveFormsModule,
        FormsModule,
        RouterModule.forChild([loginRoute])
    ],
    declarations: [
        LoginComponent
    ],
    providers: [
        LoginService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export default class LoginModule {
}
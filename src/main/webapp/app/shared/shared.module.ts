import {NgModule, CUSTOM_ELEMENTS_SCHEMA} from "@angular/core";
import {CommonModule} from "@angular/common";
import {RouterModule} from "@angular/router";

import {DividerComponent} from "./divider/divider.component";
import {TruncateCharactersPipe} from "./pipe/truncate.pipe";

const PIPES = [
    TruncateCharactersPipe
];

const COMPONENTS = [
    DividerComponent
];

@NgModule({
    imports: [
        CommonModule,
        RouterModule
    ],
    declarations: [
        ...COMPONENTS,
        ...PIPES
    ],
    exports: [
        ...COMPONENTS,
        ...PIPES
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SharedModule {
}
import {NgModule, CUSTOM_ELEMENTS_SCHEMA} from "@angular/core";
import {CommonModule} from "@angular/common";
import {TruncateCharactersPipe} from "./truncate.pipe";

const PIPES = [
    TruncateCharactersPipe
];

@NgModule({
    imports: [
        CommonModule
    ],
    declarations: [
        ...PIPES
    ],
    exports: [
        ...PIPES
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PipesModule {
}
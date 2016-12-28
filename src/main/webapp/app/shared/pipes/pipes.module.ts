import {NgModule, CUSTOM_ELEMENTS_SCHEMA} from "@angular/core";
import {CommonModule} from "@angular/common";
import {CommandPipe} from "./command.pipe";
import {KeysPipe} from "./keys.pipe";
import {ReplaceCharPipe} from "./replace.pipe";
import {SplitPipe} from "./split.pipe";
import {TruncateCharactersPipe} from "./truncate.pipe";

const PIPES = [
    CommandPipe,
    KeysPipe,
    ReplaceCharPipe,
    SplitPipe,
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
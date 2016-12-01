import {NgModule, CUSTOM_ELEMENTS_SCHEMA} from "@angular/core";
import {CommonModule} from "@angular/common";

import {CardComponent} from './card/card.component';

const COMPONENTS = [
    CardComponent
];

@NgModule({
    imports: [
        CommonModule
    ],
    declarations: [
        ...COMPONENTS
    ],
    exports: [
        ...COMPONENTS
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ComponentsModule {
}
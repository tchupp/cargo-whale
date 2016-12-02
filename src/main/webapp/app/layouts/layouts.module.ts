import {NgModule, CUSTOM_ELEMENTS_SCHEMA} from "@angular/core";
import {CommonModule} from "@angular/common";

import {CardComponent} from './card/card.component';
import {HeaderComponent} from "./header/header.component";
import {MainComponent} from "./main/main.component";
import {SidebarComponent} from "./sidebar/sidebar.component";
import {SidebarItemComponent} from "./sidebar/sidebar-item/sidebar-item.component";
import {RouterModule} from "@angular/router";

const DECLARED_COMPONENTS = [
    CardComponent,
    HeaderComponent,
    MainComponent,
    SidebarComponent,
    SidebarItemComponent
];

const EXPORTED_COMPONENTS = [
    CardComponent,
    MainComponent
];

@NgModule({
    imports: [
        CommonModule,
        RouterModule
    ],
    declarations: [
        ...DECLARED_COMPONENTS
    ],
    exports: [
        ...EXPORTED_COMPONENTS
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LayoutsModule {
}
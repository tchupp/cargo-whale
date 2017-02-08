import {NgModule, CUSTOM_ELEMENTS_SCHEMA} from "@angular/core";
import {CommonModule} from "@angular/common";
import {RouterModule} from "@angular/router";
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {CardComponent} from "./card/card.component";
import {HeaderComponent} from "./header/header.component";
import {ProfileDropdownComponent} from "./header/components/profile-dropdown/profile-dropdown.component";
import {SidebarComponent} from "./sidebar/sidebar.component";
import {SidebarItemComponent} from "./sidebar/sidebar-item/sidebar-item.component";
import {DividerComponent} from "./divider/divider.component";
import {OverlayComponent} from "./overlay/overlay.component";
import {PipesModule} from "../pipes";

const COMPONENTS = [
    CardComponent,
    HeaderComponent,
    ProfileDropdownComponent,
    DividerComponent,
    SidebarComponent,
    SidebarItemComponent,
    OverlayComponent
];

@NgModule({
    imports: [
        CommonModule,
        RouterModule,
        PipesModule,
        NgbModule.forRoot()
    ],
    declarations: [
        ...COMPONENTS
    ],
    exports: [
        ...COMPONENTS
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LayoutsModule {
}
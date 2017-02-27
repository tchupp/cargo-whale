import {NgModule, CUSTOM_ELEMENTS_SCHEMA} from "@angular/core";
import {CommonModule} from "@angular/common";
import {RouterModule} from "@angular/router";
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {CardComponent} from "./card/card.component";
import {HeaderComponent} from "./header/header.component";
import {ProfileDropdownComponent} from "./header/components/profile-dropdown/profile-dropdown.component";
import {ProfileImageDirective} from "./header/components/profile-dropdown/profile-image.directive";
import {SidebarComponent} from "./sidebar/sidebar.component";
import {SidebarItemComponent} from "./sidebar/sidebar-item/sidebar-item.component";
import {DividerComponent} from "./divider/divider.component";
import {OverlayComponent} from "./overlay/overlay.component";
import {PipesModule} from "../pipes";
import {AuthModule} from "../auth";

const COMPONENTS = [
    CardComponent,
    HeaderComponent,
    DividerComponent,
    SidebarComponent,
    SidebarItemComponent,
    OverlayComponent
];

@NgModule({
    imports: [
        AuthModule,
        CommonModule,
        RouterModule,
        PipesModule,
        NgbModule.forRoot()
    ],
    declarations: [
        ...COMPONENTS,
        ProfileDropdownComponent,
        ProfileImageDirective
    ],
    exports: [
        ...COMPONENTS
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class LayoutsModule {
}
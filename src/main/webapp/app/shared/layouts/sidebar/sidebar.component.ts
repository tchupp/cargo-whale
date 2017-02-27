import {Component, OnInit} from "@angular/core";
import {SidebarItem} from "./sidebar-item/sidebar-item.model";

@Component({
    selector: 'cw-sidebar',
    templateUrl: 'app/shared/layouts/sidebar/sidebar.html'
})
export class SidebarComponent implements OnInit {

    private sidebarItems: SidebarItem[];

    ngOnInit(): void {
        this.sidebarItems = [
            {title: 'Dashboard', icon: 'tachometer', path: '/dashboard'},
            {title: 'Containers', icon: 'server', path: '/containers'},
            {title: 'Images', icon: 'clone', path: '/images'},
            {title: 'Networks', icon: 'sitemap', path: '/networks'},
            {title: 'Volumes', icon: 'cubes', path: '/volumes'},
            {title: 'Events', icon: 'history', path: '/events'},
            {title: 'Docker', icon: 'th', path: '/docker'}];
    }
}
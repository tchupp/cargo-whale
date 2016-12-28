import {Component, OnInit} from "@angular/core";
import {Router} from "@angular/router";

@Component({
    selector: 'cw-header',
    templateUrl: 'app/shared/layouts/header/header.html'
})
export class HeaderComponent implements OnInit {

    private segments: string[];
    private activeSegment: string;

    constructor(private router: Router) {
        this.activeSegment = '';
    }

    ngOnInit(): void {
        this.router.events.subscribe(() => {
            const split = this.router.url.split('/');
            this.segments = split.slice(1, split.length - 1);
            this.activeSegment = split[split.length - 1];
        });
    }
}
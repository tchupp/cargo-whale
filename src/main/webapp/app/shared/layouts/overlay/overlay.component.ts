import {Component, trigger, transition, style, animate, OnInit} from "@angular/core";
import {
    Router,
    Event as RouterEvent,
    NavigationStart,
    NavigationEnd,
    NavigationCancel,
    NavigationError
} from "@angular/router";
import "rxjs/add/operator/debounceTime";

@Component({
    selector: 'cw-overlay',
    animations: [
        trigger(
            'fade', [
                // transition(':enter', [
                //     style({opacity: 0}),
                //     animate('500ms', style({opacity: 1}))
                // ]),
                transition(':leave', [
                    style({opacity: 1}),
                    animate('500ms', style({opacity: 0}))
                ])
            ]
        )
    ],
    template: `
<div *ngIf="loading" [@fade]>
    <div class="overlay">
        <div class="folding-cube">
            <div class="cube cube1"></div>
            <div class="cube cube2"></div>
            <div class="cube cube4"></div>
            <div class="cube cube3"></div>
        </div>
    </div>
</div>
`
})
export class OverlayComponent implements OnInit {

    private loading: boolean = true;
    private subscription;

    constructor(private router: Router) {
    }

    ngOnInit(): void {
        this.subscription = this.router.events
            .debounceTime(50)
            .subscribe((event: RouterEvent) => {
                if (event instanceof NavigationStart) {
                    this.loading = true;
                } else {
                    if (event instanceof NavigationEnd) this.loading = false;
                    if (event instanceof NavigationCancel) this.loading = false;
                    if (event instanceof NavigationError) this.loading = false;
                }
            });
    }
}
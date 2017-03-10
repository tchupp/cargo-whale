import {Directive, ElementRef, OnInit} from "@angular/core";
import {Md5} from "ts-md5/dist/md5";
import {AuthTokenService} from "../../../../auth/auth-token.service";
declare const $: JQueryStatic;

@Directive({
    selector: '[cwProfileImg]'
})
export class ProfileImageDirective implements OnInit {

    constructor(private element: ElementRef, private authTokenService: AuthTokenService) {
    }

    ngOnInit() {
        const token: string = this.authTokenService.getToken() || '';
        $(this.element.nativeElement).jdenticon(Md5.hashStr(token));
    }
}
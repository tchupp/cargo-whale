import {NgModule} from "@angular/core";
import {Ng2Webstorage} from "ng2-webstorage";
import {AuthTokenService} from "./auth-token.service";

@NgModule({
    imports: [
        Ng2Webstorage.forRoot({prefix: 'cw'}),
    ],
    providers: [
        AuthTokenService
    ]
})
export class AuthModule {
}
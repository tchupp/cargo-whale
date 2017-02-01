import {RequestOptionsArgs, Response} from "@angular/http";
import {Observable} from "rxjs/Observable";
import {HttpInterceptor} from "../http.interceptor";
import {AuthTokenService} from "../../shared/auth/auth-token.service";

export class AuthInterceptor extends HttpInterceptor {

    constructor(private authTokenService: AuthTokenService) {
        super();
    }

    requestIntercept(options?: RequestOptionsArgs): RequestOptionsArgs {
        let token = this.authTokenService.getToken();

        if (!!token) {
            options.headers.append('Authorization', 'Bearer ' + token);
        }
        return options;
    }

    responseIntercept(observable: Observable<Response>): Observable<Response> {
        return observable;
    }
}
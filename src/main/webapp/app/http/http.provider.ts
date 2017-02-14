import {Http, RequestOptions, XHRBackend} from "@angular/http";
import {InterceptableHttp} from "./interceptable-http";
import {AuthInterceptor} from "./interceptors/auth.interceptor";
import {AuthTokenService} from "../shared/auth/auth-token.service";
import {Router} from "@angular/router";
import {AuthExpiredInterceptor} from "./interceptors/auth-expired.interceptor";

export const httpProvider = () => ({
    provide: Http,
    useFactory: (backend: XHRBackend, defaultOptions: RequestOptions, authTokenService: AuthTokenService, router: Router) => {
        return new InterceptableHttp(backend, defaultOptions, [
            new AuthInterceptor(authTokenService),
            new AuthExpiredInterceptor(router)
        ])
    },
    deps: [XHRBackend, RequestOptions, AuthTokenService, Router]
});
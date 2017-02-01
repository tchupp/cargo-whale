import {Http, RequestOptions, XHRBackend} from "@angular/http";
import {InterceptableHttp} from "./interceptable-http";
import {AuthInterceptor} from "./interceptors/auth.interceptor";
import {AuthTokenService} from "../shared/auth/auth-token.service";

export const httpProvider = () => ({
    provide: Http,
    useFactory: (backend: XHRBackend, defaultOptions: RequestOptions, authTokenService: AuthTokenService) => {
        return new InterceptableHttp(backend, defaultOptions, [
            new AuthInterceptor(authTokenService),
        ])
    },
    deps: [XHRBackend, RequestOptions, AuthTokenService]
});
import {RequestOptionsArgs, Response} from "@angular/http";
import {Router} from "@angular/router";
import {Observable} from "rxjs/Observable";
import {HttpInterceptor} from "../http.interceptor";

export class AuthExpiredInterceptor extends HttpInterceptor {

    constructor(private router: Router) {
        super();
    }

    requestIntercept(options?: RequestOptionsArgs): RequestOptionsArgs {
        return options;
    }

    responseIntercept(observable: Observable<Response>): Observable<Response> {
        return observable.catch((error) => {
            if (error.status === 401) {
                this.router.navigate(['/login']);
            }

            return Observable.throw(error);
        });
    }
}
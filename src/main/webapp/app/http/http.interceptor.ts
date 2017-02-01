import {Observable} from 'rxjs/Observable';
import {Response, RequestOptionsArgs} from '@angular/http';

export abstract class HttpInterceptor {
    private _successor: HttpInterceptor = null;

    set successor(successor: HttpInterceptor) {
        this._successor = successor;
    }

    processRequestInterception(options?: RequestOptionsArgs): RequestOptionsArgs {
        return (!this._successor) ? this.requestIntercept(options) :
            this._successor.processRequestInterception(this.requestIntercept(options));
    }

    processResponseInterception(response: Observable<Response>): Observable<Response> {
        return (!this._successor) ? this.responseIntercept(response) :
            this._successor.processResponseInterception(this.responseIntercept(response));
    }

    abstract requestIntercept(options?: RequestOptionsArgs): RequestOptionsArgs;

    abstract responseIntercept(observable: Observable<Response>): Observable<Response>;

}
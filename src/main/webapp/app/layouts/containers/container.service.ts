import {Injectable} from "@angular/core";
import {Http, Response} from "@angular/http";
import {Observable} from "rxjs/Rx";

import {Container} from "./container.model";

@Injectable()
export class ContainerService {

    constructor(private http: Http) {
    }

    getAllContainers(): Observable<Container[]> {
        return this.http.get("api/containers")
            .map(ContainerService.extractContainerList)
            .catch(ContainerService.handleError);
    }

    private static extractContainerList(res: Response) {
        return res.json().containers || {};
    }

    private static handleError (error: Response | any) {
        let errMsg: string;
        if (error instanceof Response) {
            const body = error.json() || '';
            const err = body.error || JSON.stringify(body);
            errMsg = `${error.status} - ${error.statusText || ''} ${err}`;
        } else {
            errMsg = error.message ? error.message : error.toString();
        }
        console.error(errMsg);
        return Observable.throw(errMsg);
    }
}

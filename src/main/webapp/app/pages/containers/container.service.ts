import {Injectable} from "@angular/core";
import {Http} from "@angular/http";
import {Observable} from "rxjs/Rx";

import {AbstractService} from "../../shared";

@Injectable()
export class ContainerService extends AbstractService {

    constructor(private http: Http) {
        super();
    }

    getAllContainers<T>(): Observable<T> {
        return this.http.get("/api/containers")
            .map(ContainerService.extractResponseBody)
            .catch(ContainerService.handleError);
    }
}

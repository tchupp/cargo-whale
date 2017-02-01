import {Injectable} from "@angular/core";
import {Http, Response} from "@angular/http";
import {Observable} from "rxjs/Rx";

@Injectable()
export class ContainersService {

    constructor(private http: Http) {
    }

    getContainerIndex<T>(): Observable<T> {
        return this.http.get("/api/containers")
            .map((res: Response) => res.json())
            .catch((res: Response) => Observable.throw(res.toString()));
    }

    getContainerDetails<T>(id: string): Observable<T> {
        return this.http.get("/api/containers/" + id)
            .map((res: Response) => res.json())
            .catch((res: Response) => Observable.throw(res.toString()));
    }
}

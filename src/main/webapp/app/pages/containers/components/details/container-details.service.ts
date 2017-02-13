import {Injectable} from "@angular/core";
import {Http, Response} from "@angular/http";
import {Observable} from "rxjs/Rx";
import {Container} from "../container.model";

@Injectable()
export class ContainerDetailsService {

    constructor(private http: Http) {
    }

    getContainerDetails(id: string): Observable<Container> {
        return this.http.get("/api/containers/" + id)
            .map((res: Response) => res.json())
            .catch((res: Response) => Observable.throw(res.toString()));
    }
}

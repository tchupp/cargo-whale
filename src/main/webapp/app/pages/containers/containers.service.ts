import {Injectable} from "@angular/core";
import {Http, Response} from "@angular/http";
import {Observable} from "rxjs/Rx";
import {ContainerIndex} from "./components/index/container-index.model";
import {Container} from "./components/container.model";

@Injectable()
export class ContainersService {

    constructor(private http: Http) {
    }

    getContainerIndex(): Observable<ContainerIndex> {
        return this.http.get("/api/containers")
            .map((res: Response) => res.json())
            .catch((res: Response) => Observable.throw(res.toString()));
    }

    getFilteredContainerIndex(state: string): Observable<ContainerIndex> {
        return this.http.get("/api/containers?state=" + state)
            .map((res: Response) => res.json())
            .catch((res: Response) => Observable.throw(res.toString()));
    }

    getContainerDetails(id: string): Observable<Container> {
        return this.http.get("/api/containers/" + id)
            .map((res: Response) => res.json())
            .catch((res: Response) => Observable.throw(res.toString()));
    }
}

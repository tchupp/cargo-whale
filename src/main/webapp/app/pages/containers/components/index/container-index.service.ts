import {Injectable} from "@angular/core";
import {Http, Response} from "@angular/http";
import {Observable} from "rxjs/Rx";
import {ContainerIndex, ContainerIndexLinks} from "./container-index.model";

@Injectable()
export class ContainerIndexService {

    constructor(private http: Http) {
    }

    getContainerIndexLinks(): Observable<ContainerIndexLinks> {
        return this.http.get("/api/containers")
            .map((res: Response) => {
                const containerIndex: ContainerIndex = res.json();
                return containerIndex._links;
            })
            .catch((res: Response) => Observable.throw(res.toString()));
    }

    follow(links: ContainerIndexLinks, rel: string): Observable<ContainerIndex> {
        return this.http.get(links[rel].href)
            .map((res: Response) => res.json())
            .catch((res: Response) => Observable.throw(res.toString()));
    }
}

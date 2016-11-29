import {Injectable} from "@angular/core";
import {Http, Response} from "@angular/http";
import {Observable} from "rxjs/Rx";
import {ContainerSummary} from "./container-summary.model";
import {AbstractService} from "../../shared/abstract-service";

@Injectable()
export class ContainerService extends AbstractService {

    constructor(private http: Http) {
        super();
    }

    getAllContainers(): Observable<ContainerSummary[]> {
        return this.http.get("api/containers")
            .map(ContainerService.extractContainerList)
            .catch(ContainerService.handleError);
    }

    private static extractContainerList(res: Response) {
        const json = res.json() || {};
        return json.containers || {};
    }
}

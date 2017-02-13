import {Resolve, ActivatedRouteSnapshot, RouterStateSnapshot} from "@angular/router";
import {Injectable} from "@angular/core";
import {Observable} from "rxjs/Rx";
import {ContainerIndexService} from "./container-index.service";
import {ContainerIndexLinks} from "./container-index.model";

@Injectable()
export class ContainerIndexResolver implements Resolve<ContainerIndexLinks> {

    constructor(private service: ContainerIndexService) {
    }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ContainerIndexLinks> {
        return this.service.getContainerIndexLinks();
    }
}
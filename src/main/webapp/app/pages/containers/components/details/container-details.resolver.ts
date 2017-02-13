import {Resolve, ActivatedRouteSnapshot, RouterStateSnapshot} from "@angular/router";
import {Injectable} from "@angular/core";
import {Observable} from "rxjs/Rx";
import {ContainerDetailsService} from "./container-details.service";
import {Container} from "../container.model";

@Injectable()
export class ContainerDetailsResolver implements Resolve<Container> {

    constructor(private service: ContainerDetailsService) {
    }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Container> {
        let id: string = route.params['id'];

        return this.service.getContainerDetails(id);
    }
}
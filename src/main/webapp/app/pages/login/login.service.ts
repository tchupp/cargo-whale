import {Injectable} from "@angular/core";
import {Http, Response} from "@angular/http";
import {Observable} from "rxjs/Rx";
import {CredentialsModel} from "./credentials.model";

@Injectable()
export class LoginService {

    constructor(private http: Http) {
    }

    login(credentials: CredentialsModel): Observable<any> {
        return this.http.post("/api/authenticate", credentials)
            .map((res: Response) => res.json())
            .catch((res: Response) => Observable.throw(res.toString()));
    }
}
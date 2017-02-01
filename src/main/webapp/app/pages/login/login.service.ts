import {Injectable} from "@angular/core";
import {Http, Response} from "@angular/http";
import {Observable} from "rxjs/Rx";
import {CredentialsModel} from "./credentials.model";
import {LocalStorageService} from "ng2-webstorage";

@Injectable()
export class LoginService {

    private static authTokenKey = 'authenticationToken';

    constructor(private http: Http, private localStorage: LocalStorageService) {
    }

    login(credentials: CredentialsModel): Observable<any> {
        function loginSuccess(res: Response) {
            const body = res.json();

            const token = body.token;
            this.localStorage.store(LoginService.authTokenKey, token);
            return token;
        }

        return this.http.post("/api/authenticate", credentials)
            .map(loginSuccess.bind(this))
            .catch((res: Response) => Observable.throw(res.toString()));
    }
}
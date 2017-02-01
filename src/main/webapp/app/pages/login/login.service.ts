import {Injectable} from "@angular/core";
import {Http, Response} from "@angular/http";
import {Observable} from "rxjs/Rx";
import {CredentialsModel} from "./credentials.model";
import {AuthTokenService} from "../../shared/auth/auth-token.service";

@Injectable()
export class LoginService {

    constructor(private http: Http, private authTokenService: AuthTokenService) {
    }

    login(credentials: CredentialsModel): Observable<any> {
        function loginSuccess(res: Response) {
            const body = res.json();
            const token: string = body.token;

            this.authTokenService.storeToken(token);
            return token;
        }

        return this.http.post("/api/authenticate", credentials)
            .map(loginSuccess.bind(this))
            .catch((res: Response) => Observable.throw(res.toString()));
    }
}
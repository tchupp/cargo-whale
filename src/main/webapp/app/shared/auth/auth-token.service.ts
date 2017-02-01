import {Injectable} from "@angular/core";
import {LocalStorageService} from "ng2-webstorage";

@Injectable()
export class AuthTokenService {

    public static authTokenKey = 'authenticationToken';

    constructor(private localStorage: LocalStorageService) {
    }

    public getToken(): string {
        return this.localStorage.retrieve(AuthTokenService.authTokenKey);
    }

    public storeToken(token: string) {
        this.localStorage.store(AuthTokenService.authTokenKey, token);
    }
}
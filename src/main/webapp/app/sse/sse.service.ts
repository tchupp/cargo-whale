import {Injectable} from "@angular/core";
import {Observable} from "rxjs/Rx";
import {AuthTokenService} from "../shared/auth/auth-token.service";
import EventSource from "event-source";

@Injectable()
export class Sse {

    constructor(private authTokenService: AuthTokenService) {
    }

    get(url: string): Observable<any> {
        return Observable.create(observer => {
            const options = {
                headers: {Authorization: "Bearer " + this.authTokenService.getToken()}
            };

            const eventSource = new EventSource(url, options);
            eventSource.onmessage = event => observer.next(JSON.parse(event.data));
            eventSource.onerror = err => observer.error('EventSource failed ' + JSON.stringify(err));

            return () => {
                eventSource.close();
            };
        });
    }
}
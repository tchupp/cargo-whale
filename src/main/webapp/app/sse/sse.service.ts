import {Injectable} from "@angular/core";
import {Observable} from "rxjs/Rx";

@Injectable()
export class Sse {

    get(url: string): Observable<any> {
        return Observable.create(observer => {
            const eventSource = new EventSource(url);
            eventSource.onmessage = event => observer.next(JSON.parse(event.data));
            eventSource.onerror = err => observer.error('EventSource failed ' + JSON.stringify(err));

            return () => {
                eventSource.close();
            };
        });
    }
}
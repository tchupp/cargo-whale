import {Injectable} from "@angular/core";
import {Http, Response} from "@angular/http";
import {Observable} from "rxjs/Rx";

@Injectable()
export class ContainerLogsService {

    constructor(private http: Http) {
    }

    getStdOutLogs(id: string): Observable<string> {
        return this.http.get('/api/containers/' + id + '/logs?stdout=1')
            .map((res: Response) => {
                return res['_body'];
            })
            .catch((res: Response) => Observable.throw(res.toString()));
    }

    getStdErrLogs(id: string): Observable<string> {
        return this.http.get('/api/containers/' + id + '/logs?stderr=1')
            .map((res: Response) => {
                return res['_body'];
            })
            .catch((res: Response) => Observable.throw(res.toString()));
    }
}
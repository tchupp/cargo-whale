import {Injectable} from "@angular/core";
import {Observable} from "rxjs/Rx";
import {Sse} from "../../sse/sse.service";

@Injectable()
export class EventsService {

    constructor(private sse: Sse) {
    }

    followEvents(): Observable<Event> {
        return this.sse.get('/api/events?follow=true').retry();
    }

    followContainerEvents(): Observable<Event> {
        return this.sse.get('/api/events/containers?follow=true').retry();
    }
}
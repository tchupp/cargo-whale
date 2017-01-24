import {Container} from "../container.model";

export class ContainerIndex {
    _embedded: Embedded;
    stateMetadata: StateMetadata;
}

export class Embedded {
    containers: Container[];
}

export class StateMetadata {
    created: number;
    restarting: number;
    running: number;
    paused: number;
    exited: number;
    dead: number;
}
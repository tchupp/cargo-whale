import {Container} from "../container.model";

export class ContainerIndex {
    _embedded: ContainerIndexEmbedded;
    stateMetadata: StateMetadata;
    _links: ContainerIndexLinks;
}

export class ContainerIndexEmbedded {
    containers: Container[];
}

export class StateMetadata {
    all: number;
    created: number;
    restarting: number;
    running: number;
    paused: number;
    exited: number;
    dead: number;
}

export class ContainerIndexLinks {
    all: string;
    created: string;
    restarting: string;
    running: string;
    paused: string;
    exited: string;
    dead: string;
}
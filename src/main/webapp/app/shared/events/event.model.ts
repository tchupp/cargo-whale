export class Event {
    action: string;
    type: string;
    time: string;
    actor: Actor;
}

export declare type Attributes = {
    [key: string]: string;
};

export class Actor {
    id: string;
    attributes: Attributes;
}
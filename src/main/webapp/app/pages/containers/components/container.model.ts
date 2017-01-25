export class Container {
    id: string;
    image: string;
    imageId: string;
    name: string;
    state: ContainerState;
    config: ContainerConfig;
    created: number;
    labels: any;
    sizeRw: number;
    sizeRootFs: number;
    networkSettings: ContainerNetworkSettings;
    mounts: ContainerMount[];
}

export class ContainerConfig {
    hostname: string;
    attachStdin: boolean;
    attachStdout: boolean;
    attachStderr: boolean;
    portSpecs: string[];
    tty: boolean;
    openStdin: boolean;
    stdinOnce: boolean;
    env: string[];
    command: string;
    workingDir: string;
    entrypoint: string[];
    networkDisabled: boolean;
    onBuild: string[];
}

export class ContainerState {
    state: string;
    status: string;
    running: boolean;
    paused: boolean;
    restarting: boolean;
    pid: number;
    exitCode: number;
    startedAt: number;
    finishedAt: number;
    error: string;
    oomKilled: boolean;
}

export class ContainerMount {

    source: string;
    destination: string;
    mode: string;
    rw: boolean;
}

export class ContainerNetworkSettings {
    ipAddress: string;
    ipPrefixLen: number;
    gateway: string;
    bridge: string;
    ports: ContainerPortMapping[];
    macAddress: string;
    networks: any;
}

export class ContainerPortMapping {
    privatePort: number;
    publicPort: number;
    type: String;
    ip: String;
}


export class ContainerAttachedNetwork {
    networkId: string;
    endpointId: string;
    gateway: string;
    ipAddress: string;
    ipPrefixLen: number;
    ipv6Gateway: string;
    globalIPv6Address: string;
    globalIPv6PrefixLen: number;
    macAddress: string;
}

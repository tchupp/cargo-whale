import {ContainerSummary} from "./container-summary.model";
import {StateMetadata} from "./state-metadata.model";

export class ContainerIndex {
    containers: ContainerSummary[];
    stateMetadata: StateMetadata;
}
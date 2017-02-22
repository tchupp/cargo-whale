import {Component, OnInit} from "@angular/core";
import {ActivatedRoute} from "@angular/router";
import {ContainerLogsService} from "./container-logs.service";

@Component({
    selector: 'cw-container-logs',
    template: `
<div>
    <pre>{{logs}}</pre>
</div>
`
})
export class ContainerLogsComponent implements OnInit {

    private logs: string;

    constructor(private containerLogsService: ContainerLogsService, private route: ActivatedRoute) {
    }

    ngOnInit(): void {
        const id = this.route.parent.snapshot.params['id'];
        this.containerLogsService.getStdOutLogs(id).subscribe(logs => {
            this.logs = logs;
        });
    }
}
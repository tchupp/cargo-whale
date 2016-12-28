import {Pipe, PipeTransform} from "@angular/core";

@Pipe({
    name: 'command'
})
export class CommandPipe implements PipeTransform {

    transform(input: string[]): string {
        if (input === null) {
            return '';
        }
        return input.join(' ').replace('&&', '&&\n');
    }
}
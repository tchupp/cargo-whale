import {Pipe, PipeTransform} from "@angular/core";

@Pipe({
    name: 'replace'
})
export class ReplaceCharPipe implements PipeTransform {

    transform(input: string, char: string): string {
        if (input === null) {
            return '';
        }
        if (char === null) {
            return input;
        }
        return input.replace(char, '');
    }
}
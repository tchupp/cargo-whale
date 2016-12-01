import {Component, Input} from "@angular/core";

@Component({
    selector: 'cw-card',
    templateUrl: 'app/components/card/card.html'
})
export class CardComponent {
    @Input() title: String;
}

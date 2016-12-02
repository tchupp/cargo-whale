import {Component, Input} from '@angular/core';

@Component({
    selector: 'cw-card',
    templateUrl: 'app/layouts/card/card.html'
})
export class CardComponent {
    @Input() title: String;
}

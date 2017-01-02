import {Component, Input} from '@angular/core';

@Component({
    selector: 'cw-card',
    templateUrl: 'app/shared/layouts/cards/card/card.html'
})
export class CardComponent {
    @Input() title: string;
    @Input() link: boolean;
}

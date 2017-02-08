import {Component} from "@angular/core";

@Component({
    selector: 'cw-profile-dropdown',
    template: `
<div class="user-profile" ngbDropdown>
    <a class="dropdown-toggle" id="user-profile-dd" ngbDropdownToggle>
        <span class="glyphicon glyphicon-user"></span>
        <span class="caret"></span>
    </a>
    
    <ul class="dropdown-menu profile-dropdown" aria-labelledby="user-profile-dd">
        <li class="dropdown-item"><i class="dropdown-arr"></i></li>
        <li class="dropdown-item"><a><i class="fa fa-power-off"></i>Sign out</a></li>
    </ul>
</div>
`
})
export class ProfileDropdownComponent {
}
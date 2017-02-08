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
        
        <li class="dropdown-item">
            <a class="username">Username</a>
        </li>
        
        <li class="dropdown-item profile-img">
            <svg width="250" height="250" cwProfileImg></svg>
        </li>
        
        <li class="dropdown-item">
            <a class="signout"><i class="fa fa-power-off"></i>Sign out</a>
        </li>
    </ul>
</div>
`
})
export class ProfileDropdownComponent {
}
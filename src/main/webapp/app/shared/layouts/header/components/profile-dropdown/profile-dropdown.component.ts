import {Component} from "@angular/core";
import {AuthTokenService} from "../../../../auth/auth-token.service";
import {Router} from "@angular/router";

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
            <a class="signout" (click)="signOut()"><i class="fa fa-power-off"></i>Sign out</a>
        </li>
    </ul>
</div>
`
})
export class ProfileDropdownComponent {

    constructor(private router: Router, private authTokenService: AuthTokenService) {
    }

    signOut() {
        this.authTokenService.clearToken();
        this.router.navigate(['/login']);
    }
}
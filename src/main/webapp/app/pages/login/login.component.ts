import {Component, OnInit} from "@angular/core";
import {FormGroup, AbstractControl, FormBuilder, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {LoginService} from "./login.service";

@Component({
    selector: 'cw-login',
    templateUrl: 'app/pages/login/login.html'
})
export class LoginComponent implements OnInit {

    public form: FormGroup;
    public username: AbstractControl;
    public password: AbstractControl;
    public submitted: boolean = false;
    private error: boolean = false;

    constructor(private router: Router, private formBuilder: FormBuilder, private loginService: LoginService) {
    }

    ngOnInit(): void {
        this.form = this.formBuilder.group({
            'username': ['', Validators.compose([Validators.required, Validators.minLength(4)])],
            'password': ['', Validators.compose([Validators.required, Validators.minLength(4)])]
        });

        this.username = this.form.controls['username'];
        this.password = this.form.controls['password'];
    }

    public onSubmit(values: Object): void {
        this.submitted = true;

        if (this.form.valid) {
            const credentials = {
                username: values['username'],
                password: values['password']
            };

            this.loginService.login(credentials).subscribe(() => {
                this.error = false;

                this.router.navigate(['/dashboard']);
            }, () => {
                this.error = true;
            });
        }
    }
}
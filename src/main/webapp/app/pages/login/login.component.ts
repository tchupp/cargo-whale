import {Component, OnInit} from "@angular/core";
import {FormGroup, AbstractControl, FormBuilder, Validators} from "@angular/forms";
import {Router} from "@angular/router";

@Component({
    selector: 'cw-login',
    templateUrl: 'app/pages/login/login.html'
})
export class LoginComponent implements OnInit {

    public form: FormGroup;
    public user: AbstractControl;
    public password: AbstractControl;
    public submitted: boolean = false;

    constructor(private router: Router, private formBuilder: FormBuilder) {
    }

    ngOnInit(): void {
        this.form = this.formBuilder.group({
            'user': ['', Validators.compose([Validators.required, Validators.minLength(4)])],
            'password': ['', Validators.compose([Validators.required, Validators.minLength(4)])]
        });

        this.user = this.form.controls['user'];
        this.password = this.form.controls['password'];
    }

    public onSubmit(values: Object): void {
        this.submitted = true;

        if (this.form.valid) {
            console.log(values);
            this.router.navigate(['/dashboard']);
        }
    }
}
import {Component} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators, FormsModule, ReactiveFormsModule} from "@angular/forms";
import {Router} from "@angular/router";
import {AuthService} from "../../auth.service";
import {MatCard, MatCardHeader, MatCardTitle, MatCardContent} from '@angular/material/card';
import {MatFormField, MatLabel} from '@angular/material/form-field';
import {MatInput} from '@angular/material/input';
import {MatButton} from '@angular/material/button';
import {Users} from "../../shared/model/Users";

@Component({
  selector: 'app-login',
  standalone: true,
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  imports: [ MatCard, MatCardHeader, MatCardTitle, MatCardContent, FormsModule, ReactiveFormsModule, MatFormField, MatLabel, MatInput, MatButton]
})
export class LoginComponent {
  loginForm: FormGroup = new FormGroup({
    email: new FormControl,
    password: new FormControl
  });

  userInputError: boolean | undefined = false;
  pwdInputError: boolean | undefined = false;

  constructor(private loginServ: AuthService, private fb: FormBuilder, private router: Router) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required]],
      password: ['', [Validators.required]]
    });
  }

  login() {
    const user: Users = {
      userId: null,
      username: this.loginForm.get("email")?.value,
      email: "",
      password: this.loginForm.get("password")?.value,
      imageUrl: "basic",
      deviceList: null,
      role: "USER"
    }
    console.log(this.loginForm.get("email")?.value);
    if (this.loginForm.valid) {
      this.loginServ.loginUser(user);
      this.router.navigateByUrl("/devices");
    } else {
      this.userInputError = !this.loginForm.get("username")?.valid;
      this.pwdInputError = !this.loginForm.get("password")?.valid;
    }
  }

}

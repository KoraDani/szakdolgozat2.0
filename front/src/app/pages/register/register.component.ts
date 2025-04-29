import {Component} from '@angular/core';
import {Users} from "../../shared/model/Users";
import {FormBuilder, FormControl, FormGroup, Validators, FormsModule, ReactiveFormsModule} from "@angular/forms";
import {AuthService} from "../../auth.service";
import {MatCard, MatCardHeader, MatCardTitle, MatCardContent} from '@angular/material/card';
import {MatFormField, MatLabel} from '@angular/material/form-field';
import {MatInput} from '@angular/material/input';
import {MatButton} from '@angular/material/button';
import {Router} from "@angular/router";

@Component({
  selector: 'app-register',
  standalone: true,
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
  imports: [MatCard, MatCardHeader, MatCardTitle, MatCardContent, FormsModule, ReactiveFormsModule, MatFormField, MatLabel, MatInput, MatButton]
})
export class RegisterComponent {

  // registerGroup: FormGroup = new FormGroup({
  //   username: new FormControl,
  //   email: new FormControl,
  //   pwd1: new FormControl,
  //   pwd2: new FormControl
  // });
  registerGroup: FormGroup = new FormGroup({
    username: new FormControl,
    email: new FormControl,
    pwd1: new FormControl,
    pwd2: new FormControl
  });

  userInputError: boolean | undefined = false;
  emailInputError: boolean | undefined = false;
  pwd1Error: boolean | undefined = false;
  pwd2Error: boolean | undefined = false;

  constructor(private regServ: AuthService, private fb: FormBuilder,private router: Router) {
    this.registerGroup = this.fb.group({
      username: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      pwd1: ['', [Validators.required]],
      pwd2: ['', [Validators.required]]
    });
  }


  saveUser() {
    if(this.registerGroup.get("pwd1")?.value == this.registerGroup.get("pwd2")?.value){
      let user: Users = {
        userId: null,
        username: this.registerGroup.get("username")?.value,
        email: this.registerGroup.get("email")?.value,
        password: this.registerGroup.get("pwd1")?.value,
        imageUrl: "basic",
        deviceList: null,
        role: "USER"
      }
      console.log(user);
      if (this.registerGroup.get("pwd1")?.value == this.registerGroup.get("pwd2")?.value) {
        this.regServ.saveUser(user);
        this.router.navigateByUrl("/devices");
      }
    }

  }
}

import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {LoginService} from "../../shared/service/login.service";
import {Users} from "../../shared/model/Users";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {UserDTO} from "../../shared/model/dto/UserDTO";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  loginForm: FormGroup = new FormGroup({
    email: new FormControl,
    password: new FormControl
  });

  userInputError: boolean | undefined = false;
  pwdInputError: boolean | undefined= false;
  userExistError: boolean | undefined = false;

  constructor(private loginServ: LoginService, private fb: FormBuilder, private router: Router) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required]]
    });
  }

  login(){
    let userDTO: UserDTO = {
      email: this.loginForm.get("email")?.value,
      password: this.loginForm.get("password")?.value
    }
    console.log(this.loginForm.get("email")?.value);
    if(this.loginForm.valid){
      this.loginServ.loginUser(userDTO).subscribe(() =>{
        console.log("Bejelentkeztetve");
        this.router.navigateByUrl("/devices")
      },error => {
        console.error(error);
        this.userExistError = true;
      });
    }else{
      this.userInputError = !this.loginForm.get("username")?.valid;
      this.pwdInputError = !this.loginForm.get("password")?.valid;
    }
  }

}

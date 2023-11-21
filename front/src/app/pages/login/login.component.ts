import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {UserDTO} from "../../shared/model/dto/UserDTO";
import {Router} from "@angular/router";
import {AuthService} from "../../shared/service/auth.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  sessionId: any;
  loginForm: FormGroup = new FormGroup({
    email: new FormControl,
    password: new FormControl
  });

  userInputError: boolean | undefined = false;
  pwdInputError: boolean | undefined= false;
  userExistError: boolean | undefined = false;

  constructor(private loginServ: AuthService, private fb: FormBuilder, private router: Router) {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required]],
      password: ['', [Validators.required]]
    });
  }

  login(){
    let userDTO: UserDTO = {
      username:this.loginForm.get("email")?.value,
      email: this.loginForm.get("email")?.value,
      password: this.loginForm.get("password")?.value
    }
    console.log(this.loginForm.get("email")?.value);
    if(this.loginForm.valid){
      this.loginServ.loginUser(userDTO).subscribe(res =>{
        console.log("Bejelentkeztetve");
        console.log(res.sessionId);
        this.sessionId = res.sessionId;
        sessionStorage.setItem('token',this.sessionId);
        sessionStorage.setItem('userId',res.userId);
        sessionStorage.setItem('username',res.username);
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

import {Component} from '@angular/core';
import {Users} from "../../shared/model/Users";
import {RegisterService} from "../../shared/service/register.service";
import {FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import { v4 as uuid } from 'uuid';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
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
  emailInputError: boolean | undefined= false;
  pwd1Error: boolean | undefined= false;
  pwd2Error: boolean | undefined= false;

  constructor(private regServ: RegisterService, private fb: FormBuilder) {
    this.registerGroup = this.fb.group({
      username: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      pwd1: ['', [Validators.required]],
      pwd2: ['', [Validators.required]]
    });
  }



  // username = this.registerGroup.get("username")?.value;
  // email = this.registerGroup.get("email")?.value;
  // pwd1 = this.registerGroup.get("pwd1")?.value;
  // pwd2 = this.registerGroup.get("pwd2")?.value;
  // if(this.registerGroup.get("username")?.value == null){
  //   this.userInputError = true;
  // }
  // if(this.registerGroup.get("email")?.value == null){
  //   this.emailInputError = true;
  // }
  // if(this.registerGroup.get("pwd1")?.value == null){
  //   this.pwd1Error = true;
  // }
  // if(this.registerGroup.get("pwd2")?.value == null){
  //   this.pwd2Error = true;
  // }

  saveUser() {
    let user: Users = {
          // id: uuid,
          id: null,
          username: this.registerGroup.get("username")?.value,
          email: this.registerGroup.get("email")?.value,
          password: this.registerGroup.get("pwd1")?.value,
          imageUrl: "basic"
        }
    // if(this.registerGroup?.valid){
      if(this.registerGroup.get("pwd1")?.value == this.registerGroup.get("pwd2")?.value) {
        this.regServ.saveUser(user).subscribe((u: Users) => {
          // console.log("asd");
          console.log(u);
        }, error => {
          if (error.status === 400) {
            console.log("Valamelyik mező üres b+");
          }
        });
        /*  }
        }else{
          this.userInputError = !this.registerGroup.get("username")?.valid;
          this.emailInputError = !this.registerGroup.get("email")?.valid;
          this.pwd1Error = !this.registerGroup.get("pwd1")?.valid;
          this.pwd2Error = !this.registerGroup.get("pwd2")?.valid;
        }*/
      }}
}

import {Component} from '@angular/core';
import {Users} from "../../shared/model/Users";
import {RegisterService} from "../../shared/service/register.service";
import {FormControl, FormGroup, ReactiveFormsModule} from "@angular/forms";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {

  registerGroup: FormGroup = new FormGroup({
    username: new FormControl,
    email: new FormControl,
    pwd1: new FormControl,
    pwd2: new FormControl
  });

  username = this.registerGroup.get("username")?.value;
  email = this.registerGroup.get("email")?.value;
  pwd1 = this.registerGroup.get("pwd1")?.value;
  pwd2 = this.registerGroup.get("pwd2")?.value;

  constructor(private regServ: RegisterService) {
  }




  saveUser() {
    // if(this.pwd1 == this.pwd2){
    console.log("mi a fasz bajod van tegnap mág működtél");
      let user: Users = {
        id: 0,
        username: this.username,
        email: this.email,
        password: this.pwd1,
        imageUrl: "basic"
      }
      console.log(this.registerGroup);
      this.regServ.saveUser(user).subscribe(() => {
        console.log("asd");
      }, error => {
        // if(error.status === 400){
        //   console.log("Valamelyik mező üres b+");
        // }
      });
    // }
  }
}

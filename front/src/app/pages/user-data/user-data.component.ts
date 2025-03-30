import {Component, OnInit} from '@angular/core';
import {Users} from "../../shared/model/Users";
import {AuthService} from "../../auth.service";
import {FormControl, FormGroup} from "@angular/forms";

@Component({
    selector: 'app-user-data',
    templateUrl: './user-data.component.html',
    styleUrls: ['./user-data.component.scss'],
    standalone: false
})
export class UserDataComponent implements OnInit {
  user: Users| undefined;
  username: string = "";
  newPwdForm: FormGroup= new FormGroup({
    oldpwd: new FormControl,
    newpwd1: new FormControl,
    newpwd2: new FormControl
  });
  constructor(private userSer: AuthService) {
  }

  ngOnInit(): void {
    this.username = sessionStorage.getItem('username')!;
    this.userSer.getUserById(this.username).subscribe((us: Users) =>{
      this.user = us;
      console.log(us);
    },error => {
      console.error(error);
    })
  }

  changePassword(){
    console.log("username" + this.username);
    let oldpwd = this.newPwdForm.get("oldpwd")?.value;
    let newpwd1 = this.newPwdForm.get("newpwd1")?.value;
    let newpwd2 = this.newPwdForm.get("newpwd2")?.value;
    this.userSer.changePassword(this.username,oldpwd,newpwd1,newpwd2).subscribe(()=>{
      this.newPwdForm.reset();
      console.log("Password sucesfully changed");
    },error => {
      console.log(error);
    });
  }

}

import {Component, effect, OnInit} from '@angular/core';
import {AuthService} from "../../auth.service";
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule} from "@angular/forms";
import {
  MatAccordion,
  MatExpansionPanel,
  MatExpansionPanelHeader,
  MatExpansionPanelTitle
} from '@angular/material/expansion';
import {MatFormField, MatLabel} from '@angular/material/form-field';
import {MatInput} from '@angular/material/input';
import {MatButton} from '@angular/material/button';
import {UserDTO} from "../../shared/model/dto/UserDTO";

@Component({
  selector: 'app-user-data',
  standalone: true,
  templateUrl: './user-data.component.html',
  styleUrls: ['./user-data.component.scss'],
  imports: [MatAccordion, MatExpansionPanel, MatExpansionPanelHeader, MatExpansionPanelTitle, FormsModule, ReactiveFormsModule, MatFormField, MatLabel, MatInput, MatButton]
})
export class UserDataComponent implements OnInit {
  user: UserDTO | undefined;
  username: string = "";
  newPwdForm: FormGroup = new FormGroup({
    oldpwd: new FormControl,
    newpwd1: new FormControl,
    newpwd2: new FormControl
  });

  constructor(private userSer: AuthService) {
    effect(() => {
      this.user = this.userSer.currentUser();
    })
  }

  ngOnInit(): void {
    this.userSer.getUserByToken();

  }

  changePassword() {
    console.log("username" + this.username);
    let oldpwd = this.newPwdForm.get("oldpwd")?.value;
    let newpwd1 = this.newPwdForm.get("newpwd1")?.value;
    let newpwd2 = this.newPwdForm.get("newpwd2")?.value;
    this.userSer.changePassword(this.username, oldpwd, newpwd1, newpwd2).subscribe(() => {
      this.newPwdForm.reset();
      console.log("Password sucesfully changed");
    }, error => {
      console.log(error);
    });
  }

}

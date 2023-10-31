import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {LoginService} from "../../shared/service/login.service";
import {Users} from "../../shared/model/Users";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  constructor(private loginServ: LoginService) {}


}

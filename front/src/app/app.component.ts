import {Component, OnInit} from '@angular/core';
import {MatSidenav} from "@angular/material/sidenav";
import {AuthService} from "./shared/service/auth.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit{
  title = 'front';
  //TODO elvileg beállítom de akkor sem stimmel valami vele
  loggedInUser: any;

  constructor(private authServ: AuthService) {
    console.log(sessionStorage.getItem("username"));
  }


  onToggleSidenav(sidenav: MatSidenav) {
    sidenav.toggle();
  }

  onClose($event: any, sidenav: MatSidenav) {
    if ($event === true){
      sidenav.close();
    }
  }

  logout(_?: boolean) {
    //TODO itt lehet majd a kijelentkeztetésjez el kell küldeni a  usert
    this.authServ.logout();
  }

  ngOnInit(): void {
    this.loggedInUser = sessionStorage.getItem("username");
  }
}

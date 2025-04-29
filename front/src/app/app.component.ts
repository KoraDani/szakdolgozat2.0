import {Component, OnInit} from '@angular/core';
import { MatSidenav, MatSidenavContainer, MatSidenavContent } from "@angular/material/sidenav";
import {AuthService} from "./auth.service";
import { MenuComponent } from './shared/menu/menu.component';
import { MatToolbar } from '@angular/material/toolbar';
import { RouterLink, RouterOutlet } from '@angular/router';
import {NgIf} from "@angular/common";
import {MatButton} from "@angular/material/button";
import {MatIcon} from "@angular/material/icon";

@Component({
    selector: 'app-root',
    standalone: true,
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.scss'],
  imports: [MatSidenavContainer, MatSidenav, MenuComponent, MatSidenavContent, MatToolbar, RouterLink, RouterOutlet, NgIf, MatButton, MatIcon]
})
export class AppComponent implements OnInit{
  title = 'front';
  //TODO bejelentkezéskor nem frissül azonnal a menü és csak ha frissítem az oldalt akkor megjelenik jól
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
    this.loggedInUser = localStorage.getItem("token");
  }
}

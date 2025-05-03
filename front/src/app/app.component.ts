import {Component, effect, OnInit, signal} from '@angular/core';
import {MatSidenav, MatSidenavContainer, MatSidenavContent} from "@angular/material/sidenav";
import {AuthService} from "./auth.service";
import {MenuComponent} from './shared/menu/menu.component';
import {MatToolbar} from '@angular/material/toolbar';
import {RouterLink, RouterOutlet} from '@angular/router';
import {MatButton} from "@angular/material/button";
import {MatIcon} from "@angular/material/icon";

@Component({
  selector: 'app-root',
  standalone: true,
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  imports: [MatSidenavContainer, MatSidenav, MenuComponent, MatSidenavContent, MatToolbar, RouterLink, RouterOutlet, MatButton, MatIcon]
})
export class AppComponent implements OnInit {
  title = 'front';
  //TODO bejelentkezéskor nem frissül azonnal a menü és csak ha frissítem az oldalt akkor megjelenik jól
  loggedInUser: boolean = false;

  constructor(private authServ: AuthService) {
    effect(() => {
      this.loggedInUser = this.authServ.currentUser() ? true : false;
    });
  }


  onClose($event: any, sidenav: MatSidenav) {
    if ($event === true) {
      sidenav.close();
    }
  }

  logout(_?: boolean) {
    //TODO itt lehet majd a kijelentkeztetésjez el kell küldeni a  usert
    this.authServ.logout();
    this.loggedInUser = false;
  }

  ngOnInit(): void {
  }
}

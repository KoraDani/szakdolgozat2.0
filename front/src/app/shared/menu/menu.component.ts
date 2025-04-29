import {Component, EventEmitter, Input, Output} from '@angular/core';
import { RouterLink } from '@angular/router';
import { MatIcon } from '@angular/material/icon';
import { NgIf } from '@angular/common';
import {MatToolbar} from "@angular/material/toolbar";
import {MatButtonModule} from '@angular/material/button';


@Component({
    selector: 'app-menu',
    templateUrl: './menu.component.html',
    styleUrls: ['./menu.component.scss'],
  imports: [RouterLink, MatIcon, NgIf, MatToolbar,MatButtonModule]
})
export class MenuComponent {

  @Input() loggedInUser: any;
  @Output() onCloseSideNav: EventEmitter<boolean> = new EventEmitter();
  @Output() onLogout: EventEmitter<boolean> = new EventEmitter();
  close(logout?: boolean){
    if(logout === true){
      this.onLogout.emit(logout);
    }
    this.onCloseSideNav.emit(true);
  }
}

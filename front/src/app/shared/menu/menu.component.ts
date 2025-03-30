import {Component, EventEmitter, Input, Output} from '@angular/core';

@Component({
    selector: 'app-menu',
    templateUrl: './menu.component.html',
    styleUrls: ['./menu.component.scss'],
    standalone: false
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

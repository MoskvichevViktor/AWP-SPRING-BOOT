import { Component, OnInit } from '@angular/core';
import { AuthService } from "../../services/auth.service";
import {BehaviorSubject} from "rxjs";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  $username = new BehaviorSubject<string>('');
  isLoggedIn = false;

  constructor(
      private authService: AuthService
  ) { }

  ngOnInit(): void {
    this.authService.isLoggedIn.subscribe(val => this.isLoggedIn = val);
    this.authService.userProfile.subscribe(p => {
      if (p) {
        this.$username.next(p.userName);
      } else {
        this.$username.next('');
      }
    });
  }

  onLogout() {
    this.authService.signOut();
  }

}

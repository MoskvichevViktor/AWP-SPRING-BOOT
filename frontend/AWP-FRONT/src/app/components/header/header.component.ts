import { Component, OnInit } from '@angular/core';
import { AuthService } from "../../services/auth.service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  username: string | null = '';
  isLoggedIn = false;

  constructor(
      private authService: AuthService
  ) { }

  ngOnInit(): void {
    this.authService.isLoggedIn.subscribe(val => this.isLoggedIn = val);
    this.authService.loggedUser.subscribe(name => this.username = name);
  }

  onLogout() {
    this.authService.signOut();
  }

}

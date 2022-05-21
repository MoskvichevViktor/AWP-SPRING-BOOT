import { Component, OnInit } from '@angular/core';
import {NgForm} from "@angular/forms";
import { AuthRequest } from "../../../shared/models.interfaces";
import { AuthService } from "../../../services/auth.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

    isLoggedIn = false;
    loginErrors: string | null = null;

  request: AuthRequest = {
      username: '',
      password: ''
  }

  constructor(
      public authService: AuthService
  ) { }

  ngOnInit(): void {
      this.authService.isLoggedIn.subscribe(val => this.isLoggedIn = val);
      this.authService.authErrors.subscribe(val => this.loginErrors = val);
  }

  onSubmit(form: NgForm) {
      if (!form.valid) {
          return;
      }
      this.request.username = form.value.username;
      this.request.password = form.value.password;

      this.authService.signIn(this.request);
      form.resetForm();
  }

}

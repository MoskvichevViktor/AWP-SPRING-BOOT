import { Component, OnInit } from '@angular/core';
import {NgForm} from "@angular/forms";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  username = "";
  password = "";

  constructor() { }

  ngOnInit(): void {
  }

  onSubmit(form: NgForm) {
      if (!form.valid) {
          return;
      }
      console.log(form);
  }

}

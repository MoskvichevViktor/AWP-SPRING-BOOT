import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MaterialModule} from "./modules/material/material.module";
import {FlexLayoutModule} from "@angular/flex-layout";
import { LoginComponent } from './components/auth/login/login.component';
import {AppRoutingModule} from "./app-routing.module";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { MainPageComponent } from './components/main/main-page/main-page.component';
import { HTTP_INTERCEPTORS, HttpClientModule } from "@angular/common/http";
import { RequestsListComponent } from './components/requests/requests-list/requests-list.component';
import { MainPageStartComponent } from './components/main/main-page-start/main-page-start.component';
import { ClientsListComponent } from './components/clients/clients-list/clients-list.component';
import { UsersListComponent } from './components/users/users-list/users-list.component';
import { HeaderComponent } from './components/header/header.component';
import { AuthInterceptorService } from "./services/auth-interceptor.service";
import { RequestViewComponent } from './components/requests/request-view/request-view.component';
import { RequestEditComponent } from './components/requests/request-edit/request-edit.component';
@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    MainPageComponent,
    RequestsListComponent,
    MainPageStartComponent,
    ClientsListComponent,
    UsersListComponent,
    HeaderComponent,
    RequestViewComponent,
    RequestEditComponent
  ],
  imports: [
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    BrowserModule,
    BrowserAnimationsModule,
    FlexLayoutModule,
    MaterialModule,
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptorService,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

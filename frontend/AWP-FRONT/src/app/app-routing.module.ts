import { NgModule } from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {LoginComponent} from "./components/auth/login/login.component";
import {MainPageComponent} from "./components/main/main-page/main-page.component";
import {RequestsListComponent} from "./components/requests/requests-list/requests-list.component";
import {MainPageStartComponent} from "./components/main/main-page-start/main-page-start.component";


const routes: Routes = [
    { path: '', redirectTo: '/main', pathMatch: 'full' },
    { path: 'login', component: LoginComponent },
    { path: 'main', component: MainPageComponent, children: [
            { path: '', component: MainPageStartComponent },
            { path: 'requests', component: RequestsListComponent }
        ] },
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

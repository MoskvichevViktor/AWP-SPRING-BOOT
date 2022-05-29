import { NgModule } from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {LoginComponent} from "./components/auth/login/login.component";
import {MainPageComponent} from "./components/main/main-page/main-page.component";
import {RequestsListComponent} from "./components/requests/requests-list/requests-list.component";
import {MainPageStartComponent} from "./components/main/main-page-start/main-page-start.component";
import { ClientsListComponent } from "./components/clients/clients-list/clients-list.component";
import { UsersListComponent } from "./components/users/users-list/users-list.component";
import { AuthGuard } from "./guards/auth.guard";
import { RequestViewComponent } from "./components/requests/request-view/request-view.component";
import { RequestEditComponent } from "./components/requests/request-edit/request-edit.component";


const routes: Routes = [
    { path: '', redirectTo: '/main', pathMatch: 'full' },
    { path: 'login', component: LoginComponent },
    { path: 'main', component: MainPageComponent,
        canActivate: [AuthGuard],
        children: [
            { path: '', component: MainPageStartComponent },
            { path: 'requests', component: RequestsListComponent },
            { path: 'requests/new', component: RequestEditComponent },
            { path: 'requests/:id', component: RequestViewComponent },
            { path: 'requests/:id/edit', component: RequestEditComponent },
            { path: 'clients', component: ClientsListComponent },
            { path: 'users', component: UsersListComponent },
        ] },
];

@NgModule({
  declarations: [],
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

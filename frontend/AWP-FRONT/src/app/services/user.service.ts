import { Injectable } from '@angular/core';
import { User, UserRole } from "../shared/models.interfaces";
import { environment } from "../../environments/environment";
import { RemoteService } from "./remote.service";
import * as moment from 'moment';
import { map } from "rxjs";
import { formatDateTime } from "../shared/format-date-time";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private users: User[] = [];
  private loadUsersUrl = environment.api.url + environment.api.endpoints.users.list;

  constructor(
      private remoteService: RemoteService
  ) { }

  public loadAll() {
    return this.remoteService.fetchAll<User>(this.loadUsersUrl)
        .pipe(
            map(users => {
              return users.map(user => {
                  user.createdAt = formatDateTime(user.createdAt);
                  user.updatedAt = user.updatedAt ? formatDateTime(user.updatedAt) : '';
                return user;
              })
            })
        );
  }

    public renderUserRole(userRole: UserRole) {
        switch (userRole) {
            case 'ROLE_ADMIN': return 'Администратор';
            case 'ROLE_MANAGER': return 'Менеджер';
            case 'ROLE_MAIN_MANAGER': return 'Старший менеджер';
        }
        return '';
    }
}

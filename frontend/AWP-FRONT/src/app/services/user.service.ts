import { Injectable } from '@angular/core';
import { User } from "../shared/models.interfaces";
import { environment } from "../../environments/environment";
import { RemoteService } from "./remote.service";

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
    return this.remoteService.fetchAll<User>(this.loadUsersUrl);
  }
}

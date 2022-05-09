import { Injectable } from '@angular/core';
import { Client } from "../models/client";
import { RemoteService } from "./remote.service";
import { environment } from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class ClientService {

  private clients: Client[] = [];
  private loadClientsUrl = environment.api.url + environment.api.endpoints.clients.list;

  constructor(
      private remoteService: RemoteService
  ) { }

  public loadAll() {
    return this.remoteService.fetchAll<Client>(this.loadClientsUrl);
  }


}

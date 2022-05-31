import { Injectable } from '@angular/core';
import { RemoteService } from "./remote.service";
import { environment } from "../../environments/environment";
import {Client, CreditRequest} from "../shared/models.interfaces";
import {map} from "rxjs";
import {formatDateTime} from "../shared/format-date-time";

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

  public load(id: number) {
    const url = environment.api.url + environment.api.endpoints.clients.get(id);
    return this.remoteService.fetchOne<Client>(url)
        .pipe(
            map(client => {
              client.createdAt = formatDateTime(client.createdAt);
              client.updatedAt = client.updatedAt ? formatDateTime(client.updatedAt) : '';
              return client;
            })
        );
  }


}

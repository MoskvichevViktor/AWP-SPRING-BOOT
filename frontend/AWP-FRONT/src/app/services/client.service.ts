import { Injectable } from '@angular/core';
import { RemoteService } from "./remote.service";
import { environment } from "../../environments/environment";
import { Client, ClientDto, CreditRequestDto } from "../shared/models.interfaces";
import { map } from "rxjs";
import { formatDateTime } from "../shared/format-date-time";

@Injectable({
    providedIn: 'root'
})
export class ClientService {

    private loadClientsUrl = environment.api.url + environment.api.endpoints.clients.list;

    constructor(
        private remoteService: RemoteService
    ) {
    }

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

    public save(dto: ClientDto) {
        const url = environment.api.url + environment.api.endpoints.clients.create;
        this.remoteService.create<ClientDto>(url, dto).subscribe();
    }

    public update(dto: ClientDto) {
        const url = environment.api.url + environment.api.endpoints.clients.update;
        this.remoteService.update<ClientDto>(url, dto).subscribe();
    }


}

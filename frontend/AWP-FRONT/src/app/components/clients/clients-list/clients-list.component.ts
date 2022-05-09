import { Component, OnInit } from '@angular/core';
import { ClientService } from "../../../services/client.service";

@Component({
  selector: 'app-clients-list',
  templateUrl: './clients-list.component.html',
  styleUrls: ['./clients-list.component.scss']
})
export class ClientsListComponent implements OnInit {

  tableTitle = 'Список клиентов';

  constructor(
      private clientService: ClientService
  ) { }

  ngOnInit(): void {
    this.clientService.loadAll().subscribe(
        (clients) => console.log(clients)
    );
  }

}

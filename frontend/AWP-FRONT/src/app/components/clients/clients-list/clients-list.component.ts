import {AfterViewInit, Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import { ClientService } from "../../../services/client.service";
import { MatTableDataSource } from "@angular/material/table";
import { Subscription } from "rxjs";
import { Sort } from "@angular/material/sort";
import { compare } from "../../../shared/sort-compare";
import { Client } from "../../../shared/models.interfaces";
import {Router} from "@angular/router";
import {CommonFilterService} from "../../../services/common-filter.service";
import {MatPaginator} from "@angular/material/paginator";

@Component({
  selector: 'app-clients-list',
  templateUrl: './clients-list.component.html',
  styleUrls: ['./clients-list.component.scss']
})
export class ClientsListComponent implements OnInit, AfterViewInit, OnDestroy {

  @ViewChild(MatPaginator) paginator: MatPaginator | null = null;

  tableTitle = 'Список клиентов';
  dataSource = new MatTableDataSource<Client>([]);
  displayedColumns = ['menu', 'id', 'name', 'passport', 'address', 'phone'];

  $clientsSub = new Subscription();

  constructor(
      private router: Router,
      private clientService: ClientService,
      private filterService: CommonFilterService
  ) { }

  ngOnInit(): void {
    this.$clientsSub = this.clientService.loadAll().subscribe(
        clients => this.dataSource.data = clients
    );
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }

  ngOnDestroy(): void {
    this.$clientsSub.unsubscribe();
  }

  onCreateClick(id: number) {
    this.router.navigate(['/main/requests/new', {clientId: id}]);
  }

  onFilterClick(id: number) {
    this.filterService.setClientFilter(id);
    this.router.navigate(['/main/requests']);
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  sortData(sort: Sort) {
    const data = this.dataSource.data.slice();
    if (!sort.active || sort.direction === '') {
      return;
    }
    this.dataSource.data = data.sort((a: Client, b: Client) => {
      const isAsc = sort.direction === 'asc';
      switch (sort.active) {
        case 'id':
          return compare(a.id, b.id, isAsc);
        case 'name':
          return compare(a.name, b.name, isAsc);
        case 'passport':
          return compare(a.passport, b.passport, isAsc);
        case 'address':
          return compare(a.address, b.address, isAsc);
        case 'phone':
          return compare(a.phone, b.phone, isAsc);
        default:
          return 0;
      }
    });
  }

}

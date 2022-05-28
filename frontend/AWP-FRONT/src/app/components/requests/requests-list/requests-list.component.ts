import { Component, OnDestroy, OnInit } from '@angular/core';
import { CreditRequestService } from "../../../services/credit-request.service";
import { MatTableDataSource } from "@angular/material/table";
import { Sort } from "@angular/material/sort";
import { compare } from "../../../shared/sort-compare";
import { Subscription } from "rxjs";
import { CreditRequest, RequestStatus } from "../../../shared/models.interfaces";
import { FormControl } from "@angular/forms";

@Component({
  selector: 'app-requests-list',
  templateUrl: './requests-list.component.html',
  styleUrls: ['./requests-list.component.scss']
})
export class RequestsListComponent implements OnInit, OnDestroy {

  tableTitle = 'Список заявок на предоставление кредита';
  dataSource = new MatTableDataSource<CreditRequest>([]);
  displayedColumns = ['id', 'date', 'name', 'sum', 'period', 'status'];
  RequestStatus = RequestStatus;

  requestStatusFilter = new FormControl('');
  
  $requestSub = new Subscription();

  constructor(
      public creditRequestService: CreditRequestService,
  ) { }

  ngOnInit(): void {
    this.$requestSub = this.creditRequestService.loadAll().subscribe(
        requests => this.dataSource.data = requests
    );
  }

  ngOnDestroy(): void {
    this.$requestSub.unsubscribe();
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
    this.dataSource.data = data.sort((a: CreditRequest, b: CreditRequest) => {
      const isAsc = sort.direction === 'asc';
      switch (sort.active) {
        case 'id':
          return compare(a.id, b.id, isAsc);
        case 'name':
          return compare(a.clientName, b.clientName, isAsc);
        case 'date':
          return compare(a.createdAt, b.createdAt, isAsc);
        case 'sum':
          return compare(a.sum, b.sum, isAsc);
        default:
          return 0;
      }
    });
  }

  onFilterChange() {
    console.log(this.requestStatusFilter.value);
  }

}

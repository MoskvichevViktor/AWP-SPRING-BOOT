import { Component, OnDestroy, OnInit } from '@angular/core';
import { CreditRequestService } from "../../../services/credit-request.service";
import { MatTableDataSource } from "@angular/material/table";
import { Sort } from "@angular/material/sort";
import { compare } from "../../../shared/sort-compare";
import { Subscription } from "rxjs";
import { CreditRequest } from "../../../shared/models.interfaces";

@Component({
  selector: 'app-requests-list',
  templateUrl: './requests-list.component.html',
  styleUrls: ['./requests-list.component.scss']
})
export class RequestsListComponent implements OnInit, OnDestroy {

  tableTitle = 'Список заявок на предоставление кредита';
  dataSource = new MatTableDataSource<CreditRequest>([]);
  displayedColumns = ['id', 'date', 'name', 'creditSum'];
  
  $requestSub = new Subscription();

  constructor(
      private creditRequestService: CreditRequestService,
  ) { }

  ngOnInit(): void {
    this.$requestSub = this.creditRequestService.requests.subscribe(
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
          return compare(a.requesterFullName, b.requesterFullName, isAsc);
        case 'date':
          return compare(a.created.getTime(), b.created.getTime(), isAsc);
        case 'creditSum':
          return compare(a.creditSum, b.creditSum, isAsc);
        default:
          return 0;
      }
    });
  }

}

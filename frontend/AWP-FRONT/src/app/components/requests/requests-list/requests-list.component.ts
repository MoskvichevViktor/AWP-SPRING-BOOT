import {AfterViewInit, Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import { CreditRequestService } from "../../../services/credit-request.service";
import { MatTableDataSource } from "@angular/material/table";
import { Sort } from "@angular/material/sort";
import { compare } from "../../../shared/sort-compare";
import { BehaviorSubject, Subscription, switchMap } from "rxjs";
import { CreditRequest, RequestStatus } from "../../../shared/models.interfaces";
import { FormControl } from "@angular/forms";
import { ActivatedRoute, Router } from "@angular/router";
import {MatPaginator} from "@angular/material/paginator";

@Component({
  selector: 'app-requests-list',
  templateUrl: './requests-list.component.html',
  styleUrls: ['./requests-list.component.scss']
})
export class RequestsListComponent implements OnInit, AfterViewInit, OnDestroy {

  @ViewChild(MatPaginator) paginator: MatPaginator | null = null;

  tableTitle = 'Список заявок на предоставление кредита';
  dataSource = new MatTableDataSource<CreditRequest>([]);
  displayedColumns = ['menu', 'id', 'date', 'name', 'sum', 'period', 'status'];
  RequestStatus = RequestStatus;

  requestStatusFilter = new FormControl('');
  
  private $requestSub = new Subscription();
  private statusSubj = new BehaviorSubject<string>('');

  constructor(
      public creditRequestService: CreditRequestService,
      private router: Router,
      private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
    this.$requestSub = this.statusSubj
        .pipe(
        switchMap(status => this.creditRequestService.loadAll(status)))
        .subscribe(requests => this.dataSource.data = requests);
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
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
    this.statusSubj.next(this.requestStatusFilter.value);
  }

  onRequestClick(id: number) {
    this.router.navigate([id], {relativeTo: this.route});
  }

  onEditClick(id: number) {
    this.router.navigate([id, 'edit'], {relativeTo: this.route});
  }


}

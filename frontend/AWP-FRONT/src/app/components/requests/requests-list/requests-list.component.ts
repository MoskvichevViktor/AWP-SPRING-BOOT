import {AfterViewInit, Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {CreditRequestService} from "../../../services/credit-request.service";
import {MatTableDataSource} from "@angular/material/table";
import {Sort} from "@angular/material/sort";
import {compare} from "../../../shared/sort-compare";
import {BehaviorSubject, Subscription, switchMap} from "rxjs";
import {
  CreditRequest,
  CreditResponseDto,
  RequestStatus,
  ResponseStatus,
  User,
  UserRole
} from "../../../shared/models.interfaces";
import {FormControl} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {MatPaginator} from "@angular/material/paginator";
import {AuthService} from "../../../services/auth.service";
import {MatDialog} from "@angular/material/dialog";
import {ConfirmationDialogComponent} from "../../../shared/confirmation-dialog/confirmation-dialog.component";
import {CreditResponseService} from "../../../services/credit-response.service";
import {ResponseCreateComponent} from "../../responses/response-create/response-create.component";

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

  isAbleToChangeStatus = false;

  requestStatusFilter = new FormControl('');
  
  private $requestSub = new Subscription();
  private statusSubj = new BehaviorSubject<string>('');

  constructor(
      public creditRequestService: CreditRequestService,
      public creditResponseService: CreditResponseService,
      private authService: AuthService,
      private router: Router,
      private route: ActivatedRoute,
      public dialog: MatDialog,
  ) { }

  ngOnInit(): void {
    this.$requestSub = this.statusSubj
        .pipe(
        switchMap(status => this.creditRequestService.loadAll(status)))
        .subscribe(requests => {
          this.dataSource.data = requests;
          this.authService.userProfile.subscribe(
              p => this.isAbleToChangeStatus = this.checkChangeStatusRights(p)
          );
        });
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

  onUserClick(id: number) {
    this.router.navigate(['/main/clients']);
  }

  onViewClick(id: number) {
    this.router.navigate([id], {relativeTo: this.route});
  }

  onEditClick(id: number) {
    this.router.navigate([id, 'edit'], {relativeTo: this.route});
  }

  onConfirmClick(id: number) {
    this.openConfirmDialog(id);
  }

  onRejectClick(id: number) {
    this.openRejectDialog(id);
  }

  private checkChangeStatusRights(profile: User | null) {
    if (!profile) {
      return false;
    }
    return profile.role === UserRole.ROLE_MAIN_MANAGER;
  }

  openConfirmDialog(requestId: number) {
    const dialogRef = this.dialog.open(ResponseCreateComponent, {
      data: {
        requestId: requestId
      },
    });
    dialogRef.afterClosed().subscribe((result: CreditResponseDto | null) => {
      if (result) {
        this.creditResponseService.save(result).subscribe(
            () => this.router.navigate(['/main/responses'])
        );
      }
    });
  }

  openRejectDialog(requestId: number) {
      const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
        data: {
          title: 'Подтвердите операцию!',
          message: 'Вы уверены, что хотите отклонить эту заявку?',
          confirmButtonTitle: 'Да',
          cancelButtonTitle: 'Нет',
        },
      });
      dialogRef.afterClosed().subscribe(result => {
        if (result) {
          const responseDto: CreditResponseDto = {
            requestId: requestId,
            status: ResponseStatus.REJECTION
          };
          this.creditResponseService.save(responseDto).subscribe(
              () => this.router.navigate(['/main/responses'])
          );
        }
      });
  }

}

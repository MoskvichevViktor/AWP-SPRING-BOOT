import {Component, OnDestroy, OnInit} from '@angular/core';
import {CreditRequestService} from "../../../services/credit-request.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Subscription} from "rxjs";
import {CreditRequest, CreditRequestDto, RequestStatus, User, UserRole} from "../../../shared/models.interfaces";
import {MatDialog} from "@angular/material/dialog";
import {ConfirmationDialogComponent} from "../../../shared/confirmation-dialog/confirmation-dialog.component";
import {AuthService} from "../../../services/auth.service";

@Component({
  selector: 'app-request-view',
  templateUrl: './request-view.component.html',
  styleUrls: ['./request-view.component.scss']
})
export class RequestViewComponent implements OnInit, OnDestroy {

  title = 'Заявка на предоставление кредита';
  request: CreditRequest | null = null;
  isAbleToChangeStatus = false;

  private $requestSub = new Subscription();

  constructor(
      private route: ActivatedRoute,
      private router: Router,
      public dialog: MatDialog,
      public creditRequestService: CreditRequestService,
      private authService: AuthService
  ) { }

  ngOnInit(): void {
    this.$requestSub = this.route.params.subscribe(params => {
      const id = params['id'];
      this.loadRequest(id);
    });
  }

  ngOnDestroy(): void {
    this.$requestSub.unsubscribe();
  }

  private loadRequest(id: number) {
    this.creditRequestService.load(id).subscribe(
        req => {
          this.request = req;
          this.authService.userProfile.subscribe(
              p => this.isAbleToChangeStatus = this.checkChangeStatusRights(p)
          );
        }
    );
  }

  onEditClick() {
    if (!this.request) {
      return;
    }
    this.router.navigate(['edit'], {relativeTo: this.route});
  }

  getRequestInfo(request: CreditRequest | null) {
    if (!request) {
      return '';
    }
    const date = request.createdAt.split(', ')[0];
    return '№' + request.id + ' от ' + date;
  }

  openDialog(action: 'confirm' | 'reject') {
    if (action === 'confirm') {
      const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
        data: {
          title: 'Подтвердите операцию!',
          message: 'Вы уверены, что хотите одобрить заявку?',
          confirmButtonTitle: 'Да',
          cancelButtonTitle: 'Нет',
        },
      });
      dialogRef.afterClosed().subscribe(result => {
        if (result) {
          let requestDto = this.fillRequestDto(RequestStatus.CONFIRMED);
          this.saveRequest(requestDto);
        }
      });
    } else {
      const dialogRef = this.dialog.open(ConfirmationDialogComponent, {
        data: {
          title: 'Подтвердите операцию!',
          message: 'Вы уверены, что хотите отклонить заявку?',
          confirmButtonTitle: 'Да',
          cancelButtonTitle: 'Нет',
        },
      });
      dialogRef.afterClosed().subscribe(result => {
        if (result) {
          let requestDto = this.fillRequestDto(RequestStatus.REJECTION);
          this.saveRequest(requestDto);
        }
      });
    }
  }

  private fillRequestDto (status: RequestStatus): CreditRequestDto | null {
    if (this.request) {
      return {
        id: this.request.id,
        clientId: this.request.clientId,
        sum: this.request.sum,
        period: this.request.period,
        status: status
      };
    }
    return null;
}

private saveRequest(requestDto: CreditRequestDto | null) {
    if (requestDto) {
      this.creditRequestService.updateWithReply(requestDto).subscribe(() => {
        if (requestDto.id) {
          this.loadRequest(requestDto.id);
        }
      });
    }
}

private checkChangeStatusRights(profile: User | null) {
    if (!profile) {
      return false;
    }
    if (!this.request || this.request.status !== RequestStatus.WAITING) {
      return false;
    }
    return profile.role === UserRole.ROLE_MAIN_MANAGER;
}



}

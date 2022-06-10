import { Component, OnDestroy, OnInit } from '@angular/core';
import { CreditRequestService } from "../../../services/credit-request.service";
import { ActivatedRoute, Router } from "@angular/router";
import { Subscription } from "rxjs";
import { CreditRequest, RequestStatus, User, UserRole } from "../../../shared/models.interfaces";
import { MatDialog } from "@angular/material/dialog";
import { AuthService } from "../../../services/auth.service";

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
    ) {
    }

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

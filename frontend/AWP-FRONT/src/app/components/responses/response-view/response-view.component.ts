import { Component, OnDestroy, OnInit } from '@angular/core';
import { CreditResponse } from "../../../shared/models.interfaces";
import { Subscription } from "rxjs";
import { ActivatedRoute } from "@angular/router";
import { MatDialog } from "@angular/material/dialog";
import { CreditResponseService } from "../../../services/credit-response.service";

@Component({
  selector: 'app-response-view',
  templateUrl: './response-view.component.html',
  styleUrls: ['./response-view.component.scss']
})
export class ResponseViewComponent implements OnInit, OnDestroy {

    title = 'Рассмотренная заявка';
    response: CreditResponse | null = null;

    private $responseSub = new Subscription();

    constructor(
        private route: ActivatedRoute,
        public dialog: MatDialog,
        public creditResponseService: CreditResponseService
    ) {
    }

    ngOnInit(): void {
        this.$responseSub = this.route.params.subscribe(params => {
            const id = params['id'];
            this.loadResponse(id);
        });
    }

    ngOnDestroy(): void {
        this.$responseSub.unsubscribe();
    }

    private loadResponse(id: number) {
        this.creditResponseService.load(id).subscribe(
            res => this.response = res
        );
    }

    getResponseInfo(response: CreditResponse | null) {
        if (!response) {
            return '';
        }
        const date = response.createdAt.split(', ')[0];
        return '№' + response.id + ' от ' + date;
    }


}

import { Component, OnDestroy, OnInit } from '@angular/core';
import { CalcInputDto, CreditResponse } from "../../../shared/models.interfaces";
import { BehaviorSubject, Subscription } from "rxjs";
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

    calcDto: CalcInputDto = {
        sum: 1000,
        percent: 20,
        period: 12
    }

    $calcDtoSubj = new BehaviorSubject<CalcInputDto>(this.calcDto);

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
            res => {
                this.response = res;
                this.fillCalcDtoFromResponse(res);
            }
        );
    }

    private fillCalcDtoFromResponse(response: CreditResponse) {
        this.calcDto.sum = response.sum;
        this.calcDto.percent = response.percent;
        this.calcDto.period = response.period;
        this.$calcDtoSubj.next(this.calcDto);
    }

    getResponseInfo(response: CreditResponse | null) {
        if (!response) {
            return '';
        }
        const date = response.createdAt.split(', ')[0];
        return '№' + response.id + ' от ' + date;
    }


}

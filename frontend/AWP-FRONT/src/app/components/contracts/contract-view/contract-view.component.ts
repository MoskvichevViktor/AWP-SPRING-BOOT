import { Component, OnInit } from '@angular/core';
import { CalcInputDto, Contract, CreditResponse } from "../../../shared/models.interfaces";
import { BehaviorSubject, Subscription } from "rxjs";
import { ActivatedRoute } from "@angular/router";
import { MatDialog } from "@angular/material/dialog";
import { ContractService } from "../../../services/contract.service";

@Component({
    selector: 'app-contract-view',
    templateUrl: './contract-view.component.html',
    styleUrls: ['./contract-view.component.scss']
})
export class ContractViewComponent implements OnInit {

    title = 'Кредитный договор';
    contract: Contract | null = null;

    calcDto: CalcInputDto = {
        sum: 1000,
        percent: 20,
        period: 12
    }

    $calcDtoSubj = new BehaviorSubject<CalcInputDto>(this.calcDto);

    private $contractSub = new Subscription();

    constructor(
        private route: ActivatedRoute,
        public dialog: MatDialog,
        public contractService: ContractService,
    ) {
    }

    ngOnInit(): void {
        this.$contractSub = this.route.params.subscribe(params => {
            const id = params['id'];
            this.loadResponse(id);
        });
    }

    ngOnDestroy(): void {
        this.$contractSub.unsubscribe();
    }

    private loadResponse(id: number) {
        this.contractService.load(id).subscribe(
            res => {
                this.contract = res;
                this.fillCalcDtoFromContract(res);
            }
        );
    }

    private fillCalcDtoFromContract(contract: Contract) {
        this.calcDto.sum = contract.sum;
        this.calcDto.percent = contract.percent;
        this.calcDto.period = contract.period;
        this.$calcDtoSubj.next(this.calcDto);
    }

    getContractInfo(contract: Contract | null) {
        if (!contract) {
            return '';
        }
        const date = contract.createdAt.split(', ')[0];
        return '№' + contract.id + ' от ' + date;
    }

}

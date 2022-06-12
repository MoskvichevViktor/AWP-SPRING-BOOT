import { Component } from '@angular/core';
import { BehaviorSubject } from "rxjs";
import { CalcInputDto } from "../../../shared/models.interfaces";

@Component({
    selector: 'app-calculation',
    templateUrl: './calculation-controls.component.html',
    styleUrls: ['./calculation-controls.component.scss']
})
export class CalculationControlsComponent {

    title = 'Кредитный калькулятор';

    percentSliderConfig = {
        step: 1,
        minValue: 1,
        maxValue: 100,
        showThumb: true
    };

    minSum = 100;
    minPeriod = 1;

    calcDto: CalcInputDto = {
        sum: 1000,
        percent: 20,
        period: 12
    }

    creditPlans: CreditPlan[] = [
        {title: 'ипотека', percent: 9},
        {title: 'автокредит', percent: 16},
        {title: 'потребительский', percent: 18},
    ];

    $calcDtoSubj = new BehaviorSubject<CalcInputDto>(this.calcDto);

    constructor() {
    }

    onCreditPlanChange(plan: CreditPlan) {
        this.calcDto.percent = plan.percent;
        this.$calcDtoSubj.next(this.calcDto);
    }

    onCalculateClick() {
        this.$calcDtoSubj.next(this.calcDto);
    }
}

export interface CreditPlan {
    title: string,
    percent: number
}

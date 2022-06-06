import { Component, OnInit } from '@angular/core';
import {CalculationService} from "../../../services/calculation.service";
import {CalcInputDto} from "../../../shared/models.interfaces";

@Component({
  selector: 'app-calculation',
  templateUrl: './calculation.component.html',
  styleUrls: ['./calculation.component.scss']
})
export class CalculationComponent implements OnInit {

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
    sum: 100,
    percent: 20,
    period: 12
  }

  constructor(
      private calcService: CalculationService
  ) { }

  ngOnInit(): void {
  }

  onCalculateClick() {
    this.calcService.process(this.calcDto).subscribe();
  }

}

import { Component, OnInit } from '@angular/core';

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

  percentValue = 20;
  sum = 100;
  minSum = 100;
  period = 1;
  minPeriod = 1;

  constructor() { }

  ngOnInit(): void {
  }

}

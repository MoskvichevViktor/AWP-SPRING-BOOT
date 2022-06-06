import { Component, OnInit } from '@angular/core';
import { CalculationService } from "../../../services/calculation.service";
import { CalcInputDto, CalcOutputDto } from "../../../shared/models.interfaces";
import { MatTableDataSource } from "@angular/material/table";

@Component({
  selector: 'app-calculation',
  templateUrl: './calculation.component.html',
  styleUrls: ['./calculation.component.scss']
})
export class CalculationComponent {

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

  dataSource = new MatTableDataSource<CalcOutputDto>([]);
  displayedColumns = ['month', 'partPercent', 'partPayment', 'partSum'];

  constructor(
      private calcService: CalculationService
  ) { }

  onCalculateClick() {
    this.calcService.process(this.calcDto).subscribe(
        calculations  => this.dataSource.data = calculations
    );
  }

}

import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { CalcInputDto, CalcOutputDto } from "../../../shared/models.interfaces";
import { MatTableDataSource } from "@angular/material/table";
import { CalculationService } from "../../../services/calculation.service";
import { BehaviorSubject, Subscription } from "rxjs";

@Component({
  selector: 'app-calculation-list',
  templateUrl: './calculation-list.component.html',
  styleUrls: ['./calculation-list.component.scss']
})
export class CalculationListComponent implements OnInit, OnDestroy {

  @Input()
  $calcDtoSubj = new BehaviorSubject<CalcInputDto>({
    sum: 1000,
    percent: 20,
    period: 12});

  title = 'Кредитный калькулятор';

  dataSource = new MatTableDataSource<CalcOutputDto>([]);
  displayedColumns = ['month', 'partPercent', 'partPayment', 'partSum'];

  private $calcSub = new Subscription();

  constructor(
      private calcService: CalculationService
  ) { }

  ngOnInit(): void {
    this.$calcSub = this.$calcDtoSubj.subscribe(
        () => {
          this.processCalculation();
        }
    );
  }

  ngOnDestroy(): void {
    this.$calcSub.unsubscribe();
  }

  private processCalculation() {
    this.calcService.process(this.$calcDtoSubj.value).subscribe(
        calculations  => this.dataSource.data = calculations
    );
  }

}

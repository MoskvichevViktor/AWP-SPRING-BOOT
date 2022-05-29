import { Component, OnDestroy, OnInit } from '@angular/core';
import { CreditRequestService } from "../../../services/credit-request.service";
import { ActivatedRoute, Router } from "@angular/router";
import { Subscription } from "rxjs";
import { CreditRequest } from "../../../shared/models.interfaces";

@Component({
  selector: 'app-request-view',
  templateUrl: './request-view.component.html',
  styleUrls: ['./request-view.component.scss']
})
export class RequestViewComponent implements OnInit, OnDestroy {

  title = 'Заявка на предоставление кредита';
  request: CreditRequest | null = null;

  private $requestSub = new Subscription();

  constructor(
      private route: ActivatedRoute,
      private router: Router,
      public creditRequestService: CreditRequestService,
  ) { }

  ngOnInit(): void {
    this.$requestSub = this.route.params.subscribe(params => {
      const id = params['id'];
      this.creditRequestService.load(id).subscribe(
          req => this.request = req
      );
    });
  }

  ngOnDestroy(): void {
    this.$requestSub.unsubscribe();
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

}

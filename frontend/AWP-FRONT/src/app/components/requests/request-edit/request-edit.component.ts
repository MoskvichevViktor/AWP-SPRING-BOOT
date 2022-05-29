import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from "@angular/router";
import { CreditRequestService } from "../../../services/credit-request.service";
import { CreditRequest, CreditRequestDto } from "../../../shared/models.interfaces";
import { Subscription } from "rxjs";
import { FormControl, FormGroup, Validators } from "@angular/forms";

@Component({
  selector: 'app-reques-edit',
  templateUrl: './request-edit.component.html',
  styleUrls: ['./request-edit.component.scss']
})
export class RequestEditComponent implements OnInit {

  title = 'Создать новую заявку';
  request: CreditRequest | null = null;
  editMode = false;
  editForm = new FormGroup({
    clientId: new FormControl('', Validators.required),
    sum: new FormControl(0, [Validators.required, Validators.min(1)]),
    period: new FormControl(0, [Validators.required, Validators.min(1)]),
  });

  private $requestSub = new Subscription();

  constructor(
      private route: ActivatedRoute,
      public creditRequestService: CreditRequestService,
  ) { }

  ngOnInit(): void {
    this.$requestSub = this.route.params.subscribe(params => {
      this.editMode = params['id'] && params['id'] !== null;
      if (this.editMode) {
        this.title = 'Редактировать заявку';
        const id = params['id'];
        this.creditRequestService.load(id).subscribe(req => {
          this.request = req;
          this.prefillForm(req);
        });
      }
    });
  }

  ngOnDestroy(): void {
    this.$requestSub.unsubscribe();
  }

  private prefillForm(request: CreditRequest) {
    this.editForm.patchValue({
      clientId: request.clientName,
      sum: request.sum,
      period: request.period
    });
  }

  onSaveClick() {

  }

  onDiscardClick() {

  }

}

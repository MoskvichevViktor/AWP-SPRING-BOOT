import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import { CreditRequestService } from "../../../services/credit-request.service";
import {Client, CreditRequest, CreditRequestDto, FormErrors} from "../../../shared/models.interfaces";
import { Subscription } from "rxjs";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {ClientService} from "../../../services/client.service";

@Component({
  selector: 'app-reques-edit',
  templateUrl: './request-edit.component.html',
  styleUrls: ['./request-edit.component.scss']
})
export class RequestEditComponent implements OnInit {

  title = 'Создать новую заявку';
  request: CreditRequest | null = null;
  requestDto: CreditRequestDto = {
    id: null, clientId: 0, sum: 0, period: 0
  };
  editMode = false;
  editForm = this.fb.group({
    clientName: [{value: 'Client', disabled: true}, Validators.required],
    sum: [0, [Validators.required, Validators.min(1)]],
    period: [0, [Validators.required, Validators.min(1)]],
  });
  formErrors: FormErrors = {};

  private $requestSub = new Subscription();

  constructor(
      private fb: FormBuilder,
      private route: ActivatedRoute,
      private router: Router,
      public creditRequestService: CreditRequestService,
      public clientService: ClientService,
  ) { }

  ngOnInit(): void {
    this.$requestSub = this.route.params.subscribe(params => {
      this.editMode = params['id'] && params['id'] !== null;
      if (this.editMode) {
        this.title = 'Редактировать заявку';
        const id = params['id'];
        this.creditRequestService.load(id).subscribe(req => {
          this.request = req;
          this.prefillFormForEdit(req);
        });
      } else {
        const clientId = params['clientId'];
        this.clientService.load(clientId).subscribe(client => {
          this.prefillFormForCreate(client);
        });
      }
    });
  }

  ngOnDestroy(): void {
    this.$requestSub.unsubscribe();
  }

  private prefillFormForEdit(request: CreditRequest) {
    this.requestDto.id = request.id;
    this.requestDto.clientId = request.clientId;
    this.editForm.patchValue({
      clientName: request.clientName,
      sum: request.sum,
      period: request.period
    });
  }

  private prefillFormForCreate(client: Client) {
    this.requestDto.clientId = client.id;
    this.editForm.patchValue({
      clientName: client.name,
    });
  }

  onSaveClick() {
    this.requestDto.sum = this.editForm.value.sum;
    this.requestDto.period = this.editForm.value.period;

    this.checkAndShowFormErrors();
    if (this.editForm.valid) {
      this.saveOrUpdate(this.requestDto);
      this.router.navigate(['/main/requests']);
    }
  }

  onDiscardClick() {
    if (this.request) {
      this.prefillFormForEdit(this.request);
    } else {
      this.editForm.controls['sum'].reset();
      this.editForm.controls['period'].reset();
    }
    this.formErrors = {};
  }

  checkAndShowFormErrors() {
    for (const controlName in this.editForm.controls) {
      const formControl = this.editForm.controls[controlName];
      if (formControl.errors) {
        this.formErrors[controlName] = 'Проверьте правильность заполнения!'
      }
    }
  }

  saveOrUpdate(dto: CreditRequestDto) {
    if (this.editMode) {
      this.creditRequestService.update(dto);
    } else {
      this.creditRequestService.save(dto);
    }
  }

}

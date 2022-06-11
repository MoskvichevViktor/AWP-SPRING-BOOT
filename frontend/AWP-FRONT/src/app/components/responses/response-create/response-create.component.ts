import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {CreditRequestService} from "../../../services/credit-request.service";
import {CreditRequest, CreditResponseDto, FormErrors, ResponseStatus} from "../../../shared/models.interfaces";
import {FormBuilder, Validators} from "@angular/forms";

@Component({
    selector: 'app-response-create',
    templateUrl: './response-create.component.html',
    styleUrls: ['./response-create.component.scss']
})
export class ResponseCreateComponent implements OnInit {

    title = 'Ответ на кредитную заявку';
    responseDto: CreditResponseDto = {
        requestId: 0, status: ResponseStatus.CONFIRMED
    };
    editForm = this.fb.group({
        clientName: [{value: 'Client', disabled: true}],
        sum: [0, [Validators.required, Validators.min(1)]],
        period: [0, [Validators.required, Validators.min(1)]],
        percent: [14, [Validators.required, Validators.min(1)]],
    });
    formErrors: FormErrors = {};

    constructor(
        public dialogRef: MatDialogRef<ResponseCreateComponent>,
        private creditRequestService: CreditRequestService,
        private fb: FormBuilder,
        @Inject(MAT_DIALOG_DATA) public data: ResponseCreateConfig) {
    }

    ngOnInit(): void {
        this.creditRequestService.load(this.data.requestId).subscribe(
            request => {
                this.prefillFormForEdit(request);
            }
        );
    }

    onSaveClick() {
        this.responseDto.sum = this.editForm.value.sum;
        this.responseDto.period = this.editForm.value.period;
        this.responseDto.percent = this.editForm.value.percent;
        this.checkAndShowFormErrors();
        if (this.editForm.valid) {
            this.dialogRef.close(this.responseDto);
        }
    }

    onCancelClick() {
        this.dialogRef.close(null);
    }

    private prefillFormForEdit(request: CreditRequest) {
        this.responseDto.requestId = request.id;
        this.editForm.patchValue({
            clientName: request.clientName,
            sum: request.sum,
            period: request.period
        });
    }

    private checkAndShowFormErrors() {
        for (const controlName in this.editForm.controls) {
            const formControl = this.editForm.controls[controlName];
            if (formControl.errors) {
                this.formErrors[controlName] = 'Проверьте правильность заполнения!'
            }
        }
    }

}

export interface ResponseCreateConfig {
    requestId: number
}


import { Component, Inject, OnInit } from '@angular/core';
import { ContractCreateDto, ContractStatus, CreditResponse } from "../../../shared/models.interfaces";
import { FormBuilder } from "@angular/forms";
import { MAT_DIALOG_DATA, MatDialogRef } from "@angular/material/dialog";
import { CreditResponseService } from "../../../services/credit-response.service";
import { ContractService } from "../../../services/contract.service";

@Component({
    selector: 'app-contract-create',
    templateUrl: './contract-create.component.html',
    styleUrls: ['./contract-create.component.scss']
})
export class ContractCreateComponent implements OnInit {

    title = 'Создать новый договор';
    contractDto: ContractCreateDto = {
        responseId: 0, status: ContractStatus.WAITING_SIGNING
    };
    editForm = this.fb.group({
        clientName: [{value: 'Client', disabled: true}],
        sum: [{value: '0', disabled: true}],
        period: [{value: '0', disabled: true}],
        percent: [{value: '0', disabled: true}],
    });

    constructor(
        public dialogRef: MatDialogRef<ContractCreateComponent>,
        private creditResponseService: CreditResponseService,
        private contractService: ContractService,
        private fb: FormBuilder,
        @Inject(MAT_DIALOG_DATA) public data: ContractCreateConfig) {
    }

    ngOnInit(): void {
        this.creditResponseService.load(this.data.responseId).subscribe(
            response => {
                this.prefillFormForView(response);
            }
        );
    }

    onSaveClick() {
        this.contractService.save(this.contractDto).subscribe(
            () => this.dialogRef.close(this.contractDto)
        );
    }

    onCancelClick() {
        this.dialogRef.close(null);
    }

    private prefillFormForView(response: CreditResponse) {
        this.contractDto.responseId = response.id;
        this.editForm.patchValue({
            clientName: response.clientName,
            sum: response.sum,
            period: response.period,
            percent: response.percent
        });
    }

}

export interface ContractCreateConfig {
    responseId: number
}

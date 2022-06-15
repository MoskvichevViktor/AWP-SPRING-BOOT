import { Component, OnInit } from '@angular/core';
import { Client, ClientDto, CreditRequest, CreditRequestDto, FormErrors } from "../../../shared/models.interfaces";
import { FormBuilder, Validators } from "@angular/forms";
import { Subscription } from "rxjs";
import { ActivatedRoute, Router } from "@angular/router";
import { CreditRequestService } from "../../../services/credit-request.service";
import { ClientService } from "../../../services/client.service";

@Component({
  selector: 'app-client-edit',
  templateUrl: './client-edit.component.html',
  styleUrls: ['./client-edit.component.scss']
})
export class ClientEditComponent implements OnInit {

    title = 'Данные нового клиента';
    client: Client | null = null;
    clientDto: ClientDto = {
        id: null, name: '', passport: '', address: '', phone: ''
    };
    editMode = false;
    editForm = this.fb.group({
        name: ['', Validators.required],
        passport: ['', Validators.required],
        address: ['', Validators.required],
        phone: ['', Validators.required]
    });
    formErrors: FormErrors = {};

    private $clientSub = new Subscription();

    constructor(
        private fb: FormBuilder,
        private route: ActivatedRoute,
        private router: Router,
        public clientService: ClientService,
    ) { }

    ngOnInit(): void {
        this.$clientSub = this.route.params.subscribe(params => {
            this.editMode = params['id'] && params['id'] !== null;
            if (this.editMode) {
                this.title = 'Редактировать данные клиента';
                const id = params['id'];
                this.clientService.load(id).subscribe(client => {
                    this.client = client;
                    this.prefillFormForEdit(client);
                });
            }
        });
    }

    ngOnDestroy(): void {
        this.$clientSub.unsubscribe();
    }

    private prefillFormForEdit(client: Client) {
        this.clientDto.id = client.id;
        this.editForm.patchValue({
            name: client.name,
            passport: client.passport,
            address: client.address,
            phone: client.phone
        });
    }

    onSaveClick() {
        this.clientDto.name = this.editForm.value.name;
        this.clientDto.passport = this.editForm.value.passport;
        this.clientDto.address = this.editForm.value.address;
        this.clientDto.phone = this.editForm.value.phone;

        this.checkAndShowFormErrors();
        if (this.editForm.valid) {
            this.saveOrUpdate(this.clientDto);
            this.router.navigate(['/main/clients']);
        }
    }

    onDiscardClick() {
        if (this.client) {
            this.prefillFormForEdit(this.client);
        } else {
            this.editForm.controls['name'].reset();
            this.editForm.controls['passport'].reset();
            this.editForm.controls['address'].reset();
            this.editForm.controls['phone'].reset();
        }
        this.formErrors = {};
    }

    private checkAndShowFormErrors() {
        for (const controlName in this.editForm.controls) {
            const formControl = this.editForm.controls[controlName];
            if (formControl.errors) {
                this.formErrors[controlName] = 'Поле должно быть заполнено!'
            }
        }
    }

    private saveOrUpdate(dto: ClientDto) {
        if (this.editMode) {
            this.clientService.update(dto);
        } else {
            this.clientService.save(dto);
        }
    }

}

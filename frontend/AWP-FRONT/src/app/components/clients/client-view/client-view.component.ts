import { Component, OnInit } from '@angular/core';
import { Client } from "../../../shared/models.interfaces";
import { Subscription } from "rxjs";
import { ActivatedRoute, Router } from "@angular/router";
import { MatDialog } from "@angular/material/dialog";
import { ClientService } from "../../../services/client.service";

@Component({
    selector: 'app-client-view',
    templateUrl: './client-view.component.html',
    styleUrls: ['./client-view.component.scss']
})
export class ClientViewComponent implements OnInit {

    title = 'Данные клиента';
    client: Client | null = null;

    private $clientSub = new Subscription();

    constructor(
        private route: ActivatedRoute,
        private router: Router,
        public dialog: MatDialog,
        public clientService: ClientService,
    ) {
    }

    ngOnInit(): void {
        this.$clientSub = this.route.params.subscribe(params => {
            const id = params['id'];
            this.loadResponse(id);
        });
    }

    ngOnDestroy(): void {
        this.$clientSub.unsubscribe();
    }

    private loadResponse(id: number) {
        this.clientService.load(id).subscribe(
            res => this.client = res
        );
    }

    onEditClick() {
        if (!this.client) {
            return;
        }
        this.router.navigate(['edit'], {relativeTo: this.route});
    }

}

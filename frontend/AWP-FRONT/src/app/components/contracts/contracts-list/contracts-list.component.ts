import { AfterViewInit, Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { BehaviorSubject, Subscription, switchMap } from "rxjs";
import { MatPaginator } from "@angular/material/paginator";
import { MatTableDataSource } from "@angular/material/table";
import { Contract, ContractStatus } from "../../../shared/models.interfaces";
import { ContractService } from "../../../services/contract.service";
import { Sort } from "@angular/material/sort";
import { compare } from "../../../shared/sort-compare";
import { FormControl } from "@angular/forms";

@Component({
    selector: 'app-contracts-list',
    templateUrl: './contracts-list.component.html',
    styleUrls: ['./contracts-list.component.scss']
})
export class ContractsListComponent implements OnInit, AfterViewInit, OnDestroy {

    @ViewChild(MatPaginator) paginator: MatPaginator | null = null;

    tableTitle = 'Список договоров';
    dataSource = new MatTableDataSource<Contract>([]);
    displayedColumns = ['menu', 'id', 'date', 'name', 'sum', 'period', 'status'];
    ContractStatus = ContractStatus;

    contractStatusFilter = new FormControl('');

    private $requestSub = new Subscription();
    private statusSubj = new BehaviorSubject<string>('');

    constructor(
        public contractService: ContractService,
        private router: Router,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit(): void {
        this.$requestSub = this.statusSubj
            .pipe(
                switchMap(status => this.contractService.loadAll(status)))
            .subscribe(requests => this.dataSource.data = requests);
    }

    ngAfterViewInit() {
        this.dataSource.paginator = this.paginator;
    }

    ngOnDestroy(): void {
        this.$requestSub.unsubscribe();
    }

    applyFilter(event: Event) {
        const filterValue = (event.target as HTMLInputElement).value;
        this.dataSource.filter = filterValue.trim().toLowerCase();

        if (this.dataSource.paginator) {
            this.dataSource.paginator.firstPage();
        }
    }

    sortData(sort: Sort) {
        const data = this.dataSource.data.slice();
        if (!sort.active || sort.direction === '') {
            return;
        }
        this.dataSource.data = data.sort((a: Contract, b: Contract) => {
            const isAsc = sort.direction === 'asc';
            switch (sort.active) {
                case 'id':
                    return compare(a.id, b.id, isAsc);
                case 'name':
                    return compare(a.clientName, b.clientName, isAsc);
                case 'date':
                    return compare(a.createdAt, b.createdAt, isAsc);
                case 'sum':
                    return compare(a.sum, b.sum, isAsc);
                default:
                    return 0;
            }
        });
    }

    onFilterChange() {
        this.statusSubj.next(this.contractStatusFilter.value);
    }

    onUserClick(id: number) {
        this.router.navigate(['/main/clients']);
    }

    onViewClick(id: number) {
        this.router.navigate([id], {relativeTo: this.route});
    }

}

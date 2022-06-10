import { AfterViewInit, Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from "@angular/material/paginator";
import { MatTableDataSource } from "@angular/material/table";
import { CreditResponse, ResponseStatus } from "../../../shared/models.interfaces";
import { FormControl } from "@angular/forms";
import { BehaviorSubject, Subscription, switchMap } from "rxjs";
import { ActivatedRoute, Router } from "@angular/router";
import { Sort } from "@angular/material/sort";
import { compare } from "../../../shared/sort-compare";
import { CreditResponseService } from "../../../services/credit-response.service";

@Component({
    selector: 'app-responses-list',
    templateUrl: './responses-list.component.html',
    styleUrls: ['./responses-list.component.scss']
})
export class ResponsesListComponent implements OnInit, AfterViewInit, OnDestroy {

    @ViewChild(MatPaginator) paginator: MatPaginator | null = null;

    tableTitle = 'Список рассмотренных заявок';
    dataSource = new MatTableDataSource<CreditResponse>([]);
    displayedColumns = ['menu', 'id', 'date', 'name', 'sum', 'percent', 'period', 'status'];
    ResponseStatus = ResponseStatus;

    responseStatusFilter = new FormControl('');

    private $responsesSub = new Subscription();
    private statusSubj = new BehaviorSubject<string>('');

    constructor(
        public creditResponseService: CreditResponseService,
        private router: Router,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit(): void {
        this.$responsesSub = this.statusSubj
            .pipe(
                switchMap(status => this.creditResponseService.loadAll(status)))
            .subscribe(responses => this.dataSource.data = responses);
    }

    ngAfterViewInit() {
        this.dataSource.paginator = this.paginator;
    }

    ngOnDestroy(): void {
        this.$responsesSub.unsubscribe();
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
        this.dataSource.data = data.sort((a: CreditResponse, b: CreditResponse) => {
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
                case 'percent':
                    return compare(a.percent, b.percent, isAsc);
                default:
                    return 0;
            }
        });
    }

    onFilterChange() {
        this.statusSubj.next(this.responseStatusFilter.value);
    }

    onUserClick(id: number) {
        this.router.navigate(['/main/users']);
    }

    onViewClick(id: number) {
        this.router.navigate([id], {relativeTo: this.route});
    }

    onEditClick(id: number) {
        this.router.navigate([id, 'edit'], {relativeTo: this.route});
    }

}

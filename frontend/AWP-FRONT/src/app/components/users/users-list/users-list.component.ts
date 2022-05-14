import { Component, OnDestroy, OnInit } from '@angular/core';
import { MatTableDataSource } from "@angular/material/table";
import { User } from "../../../shared/models.interfaces";
import { Subscription } from "rxjs";
import { UserService } from "../../../services/user.service";
import { Sort } from "@angular/material/sort";
import { compare } from "../../../shared/sort-compare";

@Component({
  selector: 'app-users-list',
  templateUrl: './users-list.component.html',
  styleUrls: ['./users-list.component.scss']
})
export class UsersListComponent implements OnInit, OnDestroy {

  tableTitle = 'Список пользователей';
  dataSource = new MatTableDataSource<User>([]);
  displayedColumns = ['id', 'name', 'email', 'role', 'created', 'updated'];

  $usersSub = new Subscription();

  constructor(
      public userService: UserService
  ) { }

  ngOnInit(): void {
    this.$usersSub = this.userService.loadAll().subscribe(
        users => this.dataSource.data = users
    );
  }

  ngOnDestroy(): void {
    this.$usersSub.unsubscribe();
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
    this.dataSource.data = data.sort((a: User, b: User) => {
      const isAsc = sort.direction === 'asc';
      switch (sort.active) {
        case 'id':
          return compare(a.id, b.id, isAsc);
        case 'name':
          return compare(a.userName, b.userName, isAsc);
        case 'role':
          return compare(a.role, b.role, isAsc);
        case 'created':
          return compare(a.createdAt, b.createdAt, isAsc);
        case 'updated':
          return compare(a.updatedAt, b.updatedAt, isAsc);
        default:
          return 0;
      }
    });
  }

}

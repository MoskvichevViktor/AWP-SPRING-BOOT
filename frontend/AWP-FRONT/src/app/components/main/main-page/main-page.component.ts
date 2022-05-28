import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { MenuItem, UserRole } from "../../../shared/models.interfaces";
import { AuthService } from "../../../services/auth.service";
import { BehaviorSubject, map } from "rxjs";

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.scss']
})
export class MainPageComponent implements OnInit {

  private currentRoleSubj = new BehaviorSubject<UserRole>(UserRole.ROLE_MANAGER);

  menuItems: MenuItem[] = [
    {
      title: 'Главная',
      url: '',
      active: true,
      icon: 'home',
      visible: true
    },
    {
      title: 'Заявки',
      url: 'requests',
      active: false,
      icon: 'feedback',
      visible: true
    },
    {
      title: 'Рассмотренные заявки',
      url: 'responses',
      active: false,
      icon: 'thumb_up',
      visible: true
    },
    {
      title: 'Договоры',
      url: 'contracts',
      active: false,
      icon: 'assignment',
      visible: true
    },
    {
      title: 'Клиенты',
      url: 'clients',
      active: false,
      icon: 'face',
      visible: true
    },
    {
      title: 'Пользователи',
      url: 'users',
      active: false,
      icon: 'person',
      visible: true
    },
  ];

  constructor(
      private router: Router,
      private route: ActivatedRoute,
      private authService: AuthService
  ) { }

  ngOnInit(): void {
    this.authService.userProfile.subscribe(
        profile => this.currentRoleSubj.next(profile ? profile.role : UserRole.ROLE_MANAGER)
    );
  }

  onMenuItemClick(item: MenuItem) {
    this.menuItems.forEach(item => item.active = false);
    item.active = true;
    this.router.navigate([item.url], {relativeTo: this.route});
  }

}

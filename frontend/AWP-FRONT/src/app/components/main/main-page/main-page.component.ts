import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { MenuItem } from "../../../shared/models.interfaces";

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.scss']
})
export class MainPageComponent implements OnInit {
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
      private route: ActivatedRoute
  ) { }

  ngOnInit(): void {
  }

  onMenuItemClick(item: MenuItem) {
    this.menuItems.forEach(item => item.active = false);
    item.active = true;
    this.router.navigate([item.url], {relativeTo: this.route});
  }

}

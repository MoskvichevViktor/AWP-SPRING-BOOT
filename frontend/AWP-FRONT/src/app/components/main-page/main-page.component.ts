import { Component, OnInit } from '@angular/core';
import {MenuItem} from "../../models/menu-item";
import {Router} from "@angular/router";

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.scss']
})
export class MainPageComponent implements OnInit {
  menuItems: MenuItem[] = [
    {
      title: 'Главная',
      url: 'main',
      color: '',
      icon: '',
      visible: true
    },
    {
      title: 'Заявки',
      url: 'requests',
      color: '',
      icon: '',
      visible: true
    },
    {
      title: 'Рассмотренные заявки',
      url: 'responses',
      color: '',
      icon: '',
      visible: true
    },
    {
      title: 'Договоры',
      url: 'contracts',
      color: '',
      icon: '',
      visible: true
    },
  ];

  constructor(
      private router: Router
  ) { }

  ngOnInit(): void {
  }

  onMenuItemClick(item: MenuItem) {
    this.router.navigate(['/', item.url]);
  }

}

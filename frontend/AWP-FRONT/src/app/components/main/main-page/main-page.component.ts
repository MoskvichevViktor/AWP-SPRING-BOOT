import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, NavigationEnd, Router } from "@angular/router";
import { MenuItem, UserRole } from "../../../shared/models.interfaces";
import { AuthService } from "../../../services/auth.service";
import { filter } from "rxjs";

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
            active: false,
            icon: 'home',
            visible: true,
            showToRoles: [UserRole.ROLE_MANAGER, UserRole.ROLE_MAIN_MANAGER, UserRole.ROLE_ADMIN]
        },
        {
            title: 'Заявки',
            url: 'requests',
            active: false,
            icon: 'speaker_notes',
            visible: true,
            showToRoles: [UserRole.ROLE_MANAGER, UserRole.ROLE_MAIN_MANAGER, UserRole.ROLE_ADMIN]
        },
        {
            title: 'Ответы',
            url: 'responses',
            active: false,
            icon: 'feedback',
            visible: true,
            showToRoles: [UserRole.ROLE_MANAGER, UserRole.ROLE_MAIN_MANAGER, UserRole.ROLE_ADMIN]
        },
        {
            title: 'Договоры',
            url: 'contracts',
            active: false,
            icon: 'assignment',
            visible: true,
            showToRoles: [UserRole.ROLE_MANAGER, UserRole.ROLE_MAIN_MANAGER, UserRole.ROLE_ADMIN]
        },
        {
            title: 'Клиенты',
            url: 'clients',
            active: false,
            icon: 'face',
            visible: true,
            showToRoles: [UserRole.ROLE_MANAGER, UserRole.ROLE_MAIN_MANAGER, UserRole.ROLE_ADMIN]
        },
        {
            title: 'Пользователи',
            url: 'users',
            active: false,
            icon: 'person',
            visible: false,
            showToRoles: [UserRole.ROLE_ADMIN]
        },
    ];

    constructor(
        private router: Router,
        private route: ActivatedRoute,
        private authService: AuthService
    ) {
    }

    ngOnInit(): void {
        this.authService.userProfile.subscribe(
            profile => {
                const currentRole = profile ? profile.role : UserRole.ROLE_MANAGER;
                this.checkMenuItemsVisibility(currentRole);
            }
        );
        this.highlightActiveUrl();
        this.router.events.pipe(
            filter((e: any) => e instanceof NavigationEnd)
        )
            .subscribe(
                () => this.highlightActiveUrl()
            );
    }

    highlightActiveUrl() {
        const url = this.router.routerState.snapshot.url;
        const locations = url.split('/').filter(i => i !== '');
        let activeUrl: string;
        if (locations.length > 1) {
            activeUrl = locations[1].toString();
        } else {
            activeUrl = '';
        }
        this.menuItems.forEach(i => {
            if (i.url === activeUrl) {
                i.active = true;
            } else {
                i.active = false;
            }
        });
    }

    checkMenuItemsVisibility(currentRole: UserRole) {
        this.menuItems.forEach(i => {
            if (i.showToRoles.includes(currentRole)) {
                i.visible = true;
            }
        });
    }

    onMenuItemClick(item: MenuItem) {
        this.menuItems.forEach(i => i.active = false);
        item.active = true;
        this.router.navigate([item.url], {relativeTo: this.route});
    }

}

import { Injectable } from '@angular/core';
import { User, UserDto, UserRole } from "../shared/models.interfaces";
import { environment } from "../../environments/environment";
import { RemoteService } from "./remote.service";
import { map } from "rxjs";
import { formatDateTime } from "../shared/format-date-time";

@Injectable({
    providedIn: 'root'
})
export class UserService {

    private loadUsersUrl = environment.api.url + environment.api.endpoints.users.list;

    constructor(
        private remoteService: RemoteService
    ) {
    }

    public loadAll() {
        return this.remoteService.fetchAll<User>(this.loadUsersUrl)
            .pipe(
                map(users => {
                    return users.map(user => {
                        user.createdAt = formatDateTime(user.createdAt);
                        user.updatedAt = user.updatedAt ? formatDateTime(user.updatedAt) : '';
                        return user;
                    })
                })
            );
    }

    public load(id: number) {
        const url = environment.api.url + environment.api.endpoints.users.get(id);
        return this.remoteService.fetchOne<User>(url)
            .pipe(
                map(user => {
                    user.createdAt = formatDateTime(user.createdAt);
                    user.updatedAt = user.updatedAt ? formatDateTime(user.updatedAt) : '';
                    return user;
                })
            );
    }

    public save(dto: UserDto) {
        const url = environment.api.url + environment.api.endpoints.users.create;
        this.remoteService.create<UserDto>(url, dto).subscribe();
    }

    public update(dto: UserDto) {
        const url = environment.api.url + environment.api.endpoints.users.update;
        this.remoteService.update<UserDto>(url, dto).subscribe();
    }

    public renderUserRole(userRole: UserRole) {
        switch (userRole) {
            case 'ROLE_ADMIN':
                return 'Администратор';
            case 'ROLE_MANAGER':
                return 'Менеджер';
            case 'ROLE_MAIN_MANAGER':
                return 'Старший менеджер';
        }
        return '';
    }
}

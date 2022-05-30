import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { AuthRequest, AuthResponse, User } from "../shared/models.interfaces";
import { environment } from "../../environments/environment";
import { Router } from "@angular/router";
import { BehaviorSubject, catchError, of, take, tap } from "rxjs";
import { StorageService } from "./storage.service";

@Injectable({
    providedIn: 'root'
})
export class AuthService {
    private readonly JWT_TOKEN = 'JWT_TOKEN';
    private readonly REFRESH_TOKEN = 'REFRESH_TOKEN';
    private readonly USER_PROFILE = 'PROFILE';
    authErrors = new BehaviorSubject<string | null>(null);
    isLoggedIn = new BehaviorSubject<boolean>(!!this.getJwtToken());
    userProfile = new BehaviorSubject<User | null>(this.getUserProfile());

    private loginUrl = environment.api.url + environment.api.endpoints.auth.login;

    constructor(
        private http: HttpClient,
        private router: Router,
        private storageService: StorageService
    ) {
    }

    public signIn(request: AuthRequest) {
        this.http.post<AuthResponse>(this.loginUrl, request)
            .pipe(
                take(1),
                tap(authResponse => this.doLoginUser(authResponse)),
                catchError(response => {
                    if (response.error) {
                        this.handleError(response.error);
                    }
                    return of(false);
                }))
            .subscribe();
    }

    public signOut() {
        this.doLogoutUser();
    }

    public getJwtToken() {
        return this.storageService.getItem(this.JWT_TOKEN);
    }

    private getUserProfile() {
        return this.storageService.getItem(this.USER_PROFILE, 'json');
    }

    private doLoginUser(authResponse: AuthResponse) {
        this.saveCredentials(authResponse.token, authResponse.profile);
        this.isLoggedIn.next(true);
        this.userProfile.next(authResponse.profile);
        this.router.navigate(['/main']);
    }

    private doLogoutUser() {
        this.clearCredentials();
        this.router.navigate(['/login']);
        this.isLoggedIn.next(false);
        this.userProfile.next(null);
    }

    private saveCredentials(token: string, profile: User) {
        this.storageService.setItem(this.JWT_TOKEN, token);
        this.storageService.setItem(this.USER_PROFILE, profile, 'json');
    }

    private clearCredentials() {
        this.storageService.removeItem(this.JWT_TOKEN);
        this.storageService.removeItem(this.USER_PROFILE);
    }

    private handleError(error: any) {
        if (error.message) {
            this.authErrors.next(error.message);
        } else {
            this.authErrors.next('Ошибка авторизации!');
        }
    }

}

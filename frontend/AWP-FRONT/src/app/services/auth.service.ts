import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { AuthRequest, AuthResponse } from "../shared/models.interfaces";
import { environment } from "../../environments/environment";
import { Router } from "@angular/router";
import { BehaviorSubject, catchError, Observable, of, take, tap } from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class AuthService {
    private readonly JWT_TOKEN = 'JWT_TOKEN';
    private readonly REFRESH_TOKEN = 'REFRESH_TOKEN';
    private readonly USERNAME = 'USERNAME';
    authErrors = new BehaviorSubject<string | null>(null);
    isLoggedIn = new BehaviorSubject<boolean>(!!this.getJwtToken());
    loggedUser = new BehaviorSubject<string | null>(this.getUsername());

    private loginUrl = environment.api.url + environment.api.endpoints.auth.login;

    constructor(
        private http: HttpClient,
        private router: Router
    ) {
    }

    public signIn(request: AuthRequest) {
        this.http.post<AuthResponse>(this.loginUrl, request)
            .pipe(
                take(1),
                tap(res => {
                    this.doLoginUser(request.username, res.token);
                }),
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
        return localStorage.getItem(this.JWT_TOKEN);
    }

    public getUsername() {
        return localStorage.getItem(this.USERNAME) ? localStorage.getItem(this.USERNAME) : '';
    }

    private doLoginUser(username: string, token: string) {
        this.storeCredentials(username, token);
        this.isLoggedIn.next(true);
        this.loggedUser.next(username);
        this.router.navigate(['/main']);
    }

    private doLogoutUser() {
        this.clearCredentials();
        this.router.navigate(['/login']);
        this.isLoggedIn.next(false);
        this.loggedUser.next('');
    }

    private storeCredentials(username: string, token: string) {
        localStorage.setItem(this.USERNAME, username);
        localStorage.setItem(this.JWT_TOKEN, token);
    }

    private clearCredentials() {
        localStorage.removeItem(this.USERNAME);
        localStorage.removeItem(this.JWT_TOKEN);
    }

    private handleError(error: any) {
        if (error.message) {
            this.authErrors.next(error.message);
        } else {
            this.authErrors.next('Ошибка авторизации!');
        }
    }

}

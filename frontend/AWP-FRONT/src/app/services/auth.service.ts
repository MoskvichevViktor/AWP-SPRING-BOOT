import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { AuthRequest, AuthResponse } from "../shared/models.interfaces";
import { environment } from "../../environments/environment";
import { Router } from "@angular/router";
import { BehaviorSubject, catchError, of, take, tap } from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class AuthService {
    private readonly JWT_TOKEN = 'JWT_TOKEN';
    private readonly REFRESH_TOKEN = 'REFRESH_TOKEN';
    public loggedUser = new BehaviorSubject<string>('');
    public authErrors = new BehaviorSubject<string | null>(null);
    public isLoggedIn = new BehaviorSubject<boolean>(false);

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

    private doLoginUser(username: string, token: string) {
        this.isLoggedIn.next(true);
        this.loggedUser.next(username);
        this.storeToken(token);
        this.router.navigate(['/main']);
    }

    private doLogoutUser() {
        this.isLoggedIn.next(false);
        this.clearToken();
        this.loggedUser.next('');
        this.router.navigate(['/login']);
    }

    private storeToken(token: string) {
        localStorage.setItem(this.JWT_TOKEN, token);
    }

    private clearToken() {
        localStorage.removeItem(this.JWT_TOKEN);
    }

    private handleError(error: any) {
        if (error.message) {
            this.authErrors.next(error.message);
        } else {
            this.authErrors.next('ERROR!');
        }
    }

}

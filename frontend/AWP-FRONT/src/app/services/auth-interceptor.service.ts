import { Injectable } from '@angular/core';
import { HttpEvent, HttpHandler, HttpInterceptor, HttpParams, HttpRequest } from "@angular/common/http";
import { exhaustMap, Observable, take } from "rxjs";
import { AuthService } from "./auth.service";

@Injectable({
    providedIn: 'root'
})
export class AuthInterceptorService implements HttpInterceptor {

    constructor(
        private authService: AuthService
    ) {
    }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return this.authService.isLoggedIn.pipe(
            take(1),
            exhaustMap(isLoggedIn => {
                const token = this.authService.getJwtToken();
                if (!isLoggedIn || !token) {
                    return next.handle(req);
                }

                const modifiedReq = req.clone({
                    setHeaders: {
                        Auth: token,
                        'Content-Type': 'application/json; charset=utf-8'
                    }
                });
                return next.handle(modifiedReq);
            }));
    }

}

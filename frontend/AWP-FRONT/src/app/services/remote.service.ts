import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { catchError, Observable, throwError } from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class RemoteService {

  constructor(
      private http: HttpClient
  ) { }

  public fetchAll<T> (url: string): Observable<T[]> {
    return this.http.get<T[]>(url)
        .pipe(
            catchError((err) => this.handleError(err))
        );
  }

  public fetchOne<T> (url: string): Observable<T> {
    return this.http.get<T>(url)
        .pipe(
            catchError((err) => this.handleError(err))
        );
  }

  public create<T> (url: string, object: T): Observable<T> {
    return this.http.post<T>(url, object)
        .pipe(
            catchError((err) => this.handleError(err))
        );
  }

  public update<T> (url: string, object: T): Observable<T> {
    return this.http.put<T>(url, object)
        .pipe(
            catchError((err) => this.handleError(err))
        );
  }

  public delete<T> (url: string, object: T): Observable<T> {
    return this.http.delete<T>(url, object)
        .pipe(
            catchError((err) => this.handleError(err))
        );
  }

  private handleError(err: any) {
    return throwError(() => console.log(err))
  }

}

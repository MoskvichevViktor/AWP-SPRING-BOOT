import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class RemoteService {

  constructor(
      private http: HttpClient
  ) { }

  public fetchAll<T> (url: string): Observable<T[]> {
    return this.http.get<T[]>(url);
  }

  public fetchOne<T> (url: string): Observable<T> {
    return this.http.get<T>(url);
  }

  public create<T> (url: string, object: T): Observable<T> {
    return this.http.post<T>(url, object);
  }

  public update<T> (url: string, object: T): Observable<T> {
    return this.http.put<T>(url, object);
  }

  public delete<T> (url: string, object: T): Observable<T> {
    return this.http.delete<T>(url, object);
  }

}

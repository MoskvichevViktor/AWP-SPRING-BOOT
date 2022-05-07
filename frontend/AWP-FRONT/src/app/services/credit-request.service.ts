import { Injectable } from '@angular/core';
import { CreditRequest } from "../models/credit-request";
import { REQUESTS } from "../data/mock-credit-requests";
import { of } from "rxjs";


@Injectable({
  providedIn: 'root'
})
export class CreditRequestService {

  private creditRequests: CreditRequest[] = REQUESTS;

  constructor() { }

  get requests() {
    return of(this.creditRequests.slice());
  }

}

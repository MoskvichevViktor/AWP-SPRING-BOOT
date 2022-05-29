import { Injectable } from '@angular/core';
import { CreditRequest } from "../shared/models.interfaces";
import { REQUESTS } from "../data/mock-credit-requests";
import { of } from "rxjs";
import { RemoteService } from "./remote.service";
import { environment } from "../../environments/environment";


@Injectable({
  providedIn: 'root'
})
export class CreditRequestService {

  private creditRequests: CreditRequest[] = REQUESTS;
  private loadRequestsUrl = environment.api.url + environment.api.endpoints.creditRequests.list;

  constructor(
      private remoteService: RemoteService
  ) { }

  get requests() {
    return of(this.creditRequests.slice());
  }

  public loadAll() {
    this.remoteService.fetchAll<CreditRequest>(this.loadRequestsUrl)
        .subscribe(responseData => this.creditRequests = responseData);
  }

}

import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {RemoteService} from "./remote.service";
import {CommonFilterService} from "./common-filter.service";
import {
  CreditRequest,
  CreditRequestDto,
  CreditResponse,
  RequestStatus,
  ResponseStatus
} from "../shared/models.interfaces";
import {map} from "rxjs";
import {formatDateTime} from "../shared/format-date-time";

@Injectable({
  providedIn: 'root'
})
export class CreditResponseService {

  private loadRequestsUrl = environment.api.url + environment.api.endpoints.creditResponses.list;

  constructor(
      private remoteService: RemoteService,
      private filterService: CommonFilterService
  ) {
  }

  public loadAll(status?: string) {
    let url = this.loadRequestsUrl;
    let param;
    if (this.filterService.clientFilter) {
      param = this.filterService.clientFilter;
    }
    if (status) {
      this.filterService.clearClientFilter();
    }
    if (status && status !== '') {
      param = {name: 'status', value: status};
    }
    return this.remoteService.fetchAll<CreditResponse>(url, param)
        .pipe(
            map(responses => {
              return responses.map(response => {
                response.createdAt = formatDateTime(response.createdAt);
                response.updatedAt = response.updatedAt ? formatDateTime(response.updatedAt) : '';
                return response;
              })
            })
        );
  }

  public load(id: number) {
    const url = environment.api.url + environment.api.endpoints.creditResponses.get(id);
    return this.remoteService.fetchOne<CreditResponse>(url)
        .pipe(
            map(response => {
              response.createdAt = formatDateTime(response.createdAt);
              response.updatedAt = response.updatedAt ? formatDateTime(response.updatedAt) : '';
              return response;
            })
        );
  }

  public save(dto: CreditRequestDto) {
    const url = environment.api.url + environment.api.endpoints.creditRequests.create;
    this.remoteService.create<CreditRequestDto>(url, dto).subscribe();
  }

  public update(dto: CreditRequestDto) {
    const url = environment.api.url + environment.api.endpoints.creditRequests.update;
    this.remoteService.update<CreditRequestDto>(url, dto).subscribe();
  }

  public updateWithReply(dto: CreditRequestDto) {
    const url = environment.api.url + environment.api.endpoints.creditRequests.update;
    return this.remoteService.update<CreditRequestDto>(url, dto);
  }

  public renderResponseStatus(status: ResponseStatus) {
    switch (status) {
      case 'WAITING':
        return 'Ожидает решения';
      case 'CONFIRMED':
        return 'Одобрена';
      case 'REJECTION':
        return 'Отклонена';
    }
    return '';
  }
}

import { Injectable } from '@angular/core';
import { CreditRequest, RequestStatus, UserRole } from "../shared/models.interfaces";
import { map } from "rxjs";
import { RemoteService } from "./remote.service";
import { environment } from "../../environments/environment";
import * as moment from "moment";


@Injectable({
  providedIn: 'root'
})
export class CreditRequestService {

  private loadRequestsUrl = environment.api.url + environment.api.endpoints.creditRequests.list;

  constructor(
      private remoteService: RemoteService
  ) { }

  public loadAll() {
    return this.remoteService.fetchAll<CreditRequest>(this.loadRequestsUrl)
        .pipe(
            map(requests => {
              return requests.map(request => {
                request.createdAt = moment(request.createdAt).format('DD.MM.YYYY, HH:mm:ss');
                request.updatedAt = request.updatedAt ? moment(request.updatedAt).format('DD.MM.YYYY, HH:mm:ss') : '';
                return request;
              })
            })
        );
  }

    public renderRequestStatus(status: RequestStatus) {
        switch (status) {
            case 'WAITING': return 'Ожидает решения';
            case 'CONFIRMED': return 'Одобрена';
            case 'REJECTION': return 'Отклонена';
        }
        return '';
    }

}

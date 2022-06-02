import { Injectable } from '@angular/core';
import {CreditRequest, CreditRequestDto, RequestStatus} from "../shared/models.interfaces";
import { map } from "rxjs";
import { RemoteService } from "./remote.service";
import { environment } from "../../environments/environment";
import { formatDateTime } from "../shared/format-date-time";
import {CommonFilterService} from "./common-filter.service";


@Injectable({
    providedIn: 'root'
})
export class CreditRequestService {

    private loadRequestsUrl = environment.api.url + environment.api.endpoints.creditRequests.list;

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
        return this.remoteService.fetchAll<CreditRequest>(url, param)
            .pipe(
                map(requests => {
                    return requests.map(request => {
                        request.createdAt = formatDateTime(request.createdAt);
                        request.updatedAt = request.updatedAt ? formatDateTime(request.updatedAt) : '';
                        return request;
                    })
                })
            );
    }

    public load(id: number) {
        const url = environment.api.url + environment.api.endpoints.creditRequests.get(id);
        return this.remoteService.fetchOne<CreditRequest>(url)
            .pipe(
                map(request => {
                    request.createdAt = formatDateTime(request.createdAt);
                    request.updatedAt = request.updatedAt ? formatDateTime(request.updatedAt) : '';
                    return request;
                })
            );
    }

    public save(dto: CreditRequestDto) {
        const url = environment.api.url + environment.api.endpoints.creditRequests.create;
        this.remoteService.create<CreditRequestDto>(url, dto).subscribe();
    }

    public update(dto: CreditRequestDto) {
        if (dto.id) {
            const url = environment.api.url + environment.api.endpoints.creditRequests.update;
            this.remoteService.update<CreditRequestDto>(url, dto).subscribe();
        }
    }

    public renderRequestStatus(status: RequestStatus) {
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

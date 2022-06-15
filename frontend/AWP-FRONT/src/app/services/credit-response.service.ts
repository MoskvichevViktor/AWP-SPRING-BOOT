import { Injectable } from '@angular/core';
import { environment } from "../../environments/environment";
import { RemoteService } from "./remote.service";
import { CommonFilterService } from "./common-filter.service";
import { CreditResponse, CreditResponseDto, ResponseStatus } from "../shared/models.interfaces";
import { map } from "rxjs";
import { formatDateTime } from "../shared/format-date-time";

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
                        response.sum = response.sum ? response.sum : 0;
                        response.period = response.period ? response.period : 0;
                        response.percent = response.percent ? response.percent : 0;
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

    public save(dto: CreditResponseDto) {
        const url = environment.api.url + environment.api.endpoints.creditResponses.create;
        return this.remoteService.create<CreditResponseDto>(url, dto);
    }

    public update(dto: CreditResponseDto) {
        const url = environment.api.url + environment.api.endpoints.creditResponses.update;
        this.remoteService.update<CreditResponseDto>(url, dto).subscribe();
    }

    public renderResponseStatus(status: ResponseStatus) {
        switch (status) {
            case 'CONFIRMED':
                return 'Заявка одобрена';
            case 'REJECTION':
                return 'Заявка отклонена';
            case 'PROCESSED':
                return 'Оформлен договор';
        }
        return '';
    }
}

import { Injectable } from '@angular/core';
import {RemoteService} from "./remote.service";
import {environment} from "../../environments/environment";
import {Contract, ContractStatus } from "../shared/models.interfaces";
import {map} from "rxjs";
import {formatDateTime} from "../shared/format-date-time";
import {CommonFilterService} from "./common-filter.service";

@Injectable({
  providedIn: 'root'
})
export class ContractService {

  private loadContractsUrl = environment.api.url + environment.api.endpoints.contracts.list;

  constructor(
      private remoteService: RemoteService,
      private filterService: CommonFilterService
  ) { }

  public loadAll(status?: string) {
    let url = this.loadContractsUrl;
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
    return this.remoteService.fetchAll<Contract>(url, param)
        .pipe(
            map(contracts => {
              return contracts.map(contract => {
                contract.createdAt = formatDateTime(contract.createdAt);
                contract.updatedAt = contract.updatedAt ? formatDateTime(contract.updatedAt) : '';
                return contract;
              })
            })
        );
  }

  public renderContractStatus(status: ContractStatus) {
    switch (status) {
      case 'WAITING_SIGNING':
        return 'Ожидает подписания клиентом';
      case 'ACTIVE':
        return 'Действующий';
      case 'COMPLETED':
        return 'Завершенный';
    }
    return '';
  }

}

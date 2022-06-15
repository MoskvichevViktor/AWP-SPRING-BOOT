import { Injectable } from '@angular/core';
import {RemoteService} from "./remote.service";
import {environment} from "../../environments/environment";
import {
  Contract,
  ContractCreateDto,
  ContractStatus,
  ContractUpdateDto,
  CreditResponse,
  CreditResponseDto
} from "../shared/models.interfaces";
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

  public load(id: number) {
    const url = environment.api.url + environment.api.endpoints.contracts.get(id);
    return this.remoteService.fetchOne<Contract>(url)
        .pipe(
            map(contract => {
              contract.createdAt = formatDateTime(contract.createdAt);
              contract.updatedAt = contract.updatedAt ? formatDateTime(contract.updatedAt) : '';
              return contract;
            })
        );
  }

  public save(dto: ContractCreateDto) {
    const url = environment.api.url + environment.api.endpoints.contracts.create;
    return this.remoteService.create<ContractCreateDto>(url, dto);
  }

  public update(dto: ContractUpdateDto) {
    const url = environment.api.url + environment.api.endpoints.contracts.update;
    return this.remoteService.update<ContractUpdateDto>(url, dto);
  }

  public renderContractStatus(status: ContractStatus) {
    switch (status) {
      case 'WAITING_SIGNING':
        return 'Ожидает подписания';
      case 'ACTIVE':
        return 'Действующий';
      case 'COMPLETED':
        return 'Завершенный';
    }
    return '';
  }

}

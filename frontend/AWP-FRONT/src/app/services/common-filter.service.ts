import { Injectable } from '@angular/core';
import {Param} from "../shared/models.interfaces";

@Injectable({
  providedIn: 'root'
})
export class CommonFilterService {

  private clientId: number | null = null;

  constructor() { }

  get clientFilter() {
    if (this.clientId) {
      const filter: Param = {name: 'clientId', value: this.clientId.toString()};
      return filter;
    }
    return null;
  }

  setClientFilter(clientId: number | null) {
    this.clientId = clientId;
  }

  clearClientFilter() {
    this.clientId = null;
  }

}

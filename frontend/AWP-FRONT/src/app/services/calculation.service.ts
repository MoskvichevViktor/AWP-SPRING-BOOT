import { Injectable } from '@angular/core';
import {RemoteService} from "./remote.service";
import {CalcInputDto, CreditRequestDto} from "../shared/models.interfaces";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class CalculationService {

  constructor(
      private remoteService: RemoteService
  ) { }

  public process(dto: CalcInputDto) {
    const url = environment.api.url + environment.api.endpoints.calculation.process;
    return this.remoteService.create<CalcInputDto>(url, dto);
  }

}

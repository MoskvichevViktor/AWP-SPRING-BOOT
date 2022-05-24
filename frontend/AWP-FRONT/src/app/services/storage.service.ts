import {Injectable} from '@angular/core';
import {User} from "../shared/models.interfaces";

@Injectable({
    providedIn: 'root'
})
export class StorageService {

    private prefix = 'awp_';

    constructor() {
    }

    public getItem(name: string, type: 'number'|'json'|'any' = 'any') {
        const valName = this.prefix + name;
        switch (type) {
            case 'json':
                return JSON.parse(localStorage.getItem(valName) || '{}');
            case 'number':
                return parseInt(localStorage.getItem(valName) || '0', 0);
            default:
                return localStorage.getItem(valName) ? localStorage.getItem(valName) : '';
        }
    }

    public setItem(name: string, value: any, type: 'number'|'json'|'any' = 'any') {
        switch (type) {
            case 'json': localStorage.setItem(this.prefix + name, JSON.stringify(value) );
            break;
            default: localStorage.setItem(this.prefix + name, value );
            break;
        }
    }

    public removeItem(name: string) {
        localStorage.removeItem(this.prefix + name);
    }

}

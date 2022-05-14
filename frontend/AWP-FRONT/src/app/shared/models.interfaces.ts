export interface Client {
    id: number,
    name: string,
    pasport: string,
    adress: string,
    phone: string
}

export interface MenuItem {
    title: string,
    url: string,
    icon?: string,
    active: boolean,
    visible: boolean
}

export interface CreditRequest {
    id: number,
    requesterId: number,
    requesterFullName: string,
    creditSum: number,
    actual: boolean,
    created: Date
}

export interface User {
    id: number,
    name: string,
    email: string,
    role: UserRole,
    created: Date,
    updated: Date
}

export enum UserRole {
    ROLE_ADMIN = "ROLE_ADMIN",
    ROLE_MANAGER = "ROLE_MANAGER",
    ROLE_MAIN_MANAGER = "ROLE_MAIN_MANAGER",
}
export interface AuthRequest {
    username: string,
    password: string
}

export interface AuthResponse {
    token: string
}

export interface Client {
    id: number,
    name: string,
    passport: string,
    address: string,
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
    userName: string,
    email: string,
    role: UserRole,
    createdAt: string,
    updatedAt: string
}

export enum UserRole {
    ROLE_ADMIN = "ROLE_ADMIN",
    ROLE_MANAGER = "ROLE_MANAGER",
    ROLE_MAIN_MANAGER = "ROLE_MAIN_MANAGER",
}
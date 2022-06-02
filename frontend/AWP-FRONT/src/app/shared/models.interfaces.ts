export interface AuthRequest {
    username: string,
    password: string
}

export interface AuthResponse {
    token: string,
    profile: User
}

export interface Client {
    id: number,
    name: string,
    passport: string,
    address: string,
    phone: string,
    createdAt: string,
    updatedAt: string
}

export interface CreditRequest {
    id: number,
    clientId: number,
    clientName: string,
    period: number
    sum: number,
    status: RequestStatus
    createdAt: string,
    updatedAt: string
}

export interface CreditRequestDto {
    id: number | null,
    clientId: number,
    period: number
    sum: number
}

export interface FormErrors {
    [key: string]: string
}

export interface MenuItem {
    title: string,
    url: string,
    icon?: string,
    active: boolean,
    visible: boolean,
    showToRoles: UserRole[]
}

export interface Param {
    name: string,
    value: string
}

export interface User {
    id: number,
    userName: string,
    email: string,
    role: UserRole,
    createdAt: string,
    updatedAt: string
}

export enum RequestStatus {
    WAITING = "WAITING",
    CONFIRMED = "CONFIRMED",
    REJECTION = "REJECTION",
}

export enum UserRole {
    ROLE_ADMIN = "ROLE_ADMIN",
    ROLE_MANAGER = "ROLE_MANAGER",
    ROLE_MAIN_MANAGER = "ROLE_MAIN_MANAGER",
}
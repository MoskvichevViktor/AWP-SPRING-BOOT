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
    clientId: number,
    clientName: string,
    period: number
    sum: number,
    status: RequestStatus
    createdAt: string,
    updatedAt: string
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

export enum RequestStatus {
    WAITING = "WAITING",
    CONFIRMED = "CONFIRMED",
    REJECTION = "REJECTION",
}
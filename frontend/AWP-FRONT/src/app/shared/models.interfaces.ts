export interface AuthRequest {
    username: string,
    password: string
}

export interface AuthResponse {
    token: string,
    profile: User
}

export interface CalcInputDto {
    sum: number,
    percent: number,
    period: number
}

export interface CalcOutputDto {
    month: number,
    partPercent: number,
    partPayment: number,
    partSum: number
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

export interface ClientDto {
    id: number | null,
    name: string,
    passport: string,
    address: string,
    phone: string
}

export interface Contract {
    id: number,
    clientId: number,
    clientName: string,
    period: number,
    sum: number,
    percent: number,
    status: ContractStatus,
    createdAt: string,
    updatedAt: string
}

export interface ContractCreateDto {
    responseId: number,
    status: ContractStatus
}

export interface ContractUpdateDto {
    contractId: number,
    status: ContractStatus
}

export interface CreditRequest {
    id: number,
    clientId: number,
    clientName: string,
    period: number,
    sum: number,
    status: RequestStatus,
    createdAt: string,
    updatedAt: string
}

export interface CreditResponse {
    id: number,
    clientId: number,
    clientName: string,
    contractId: number,
    period: number,
    sum: number,
    percent: number,
    status: ResponseStatus,
    createdAt: string,
    updatedAt: string
}

export interface CreditRequestDto {
    id: number | null,
    clientId: number,
    period: number,
    sum: number,
    status?: RequestStatus
}

export interface CreditResponseDto {
    requestId: number,
    period?: number,
    sum?: number,
    percent?: number,
    status: ResponseStatus
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

export interface UserDto {
    id?: number,
    userName: string,
    email: string,
    password: string,
    role: UserRole
}

export enum ContractStatus {
    WAITING_SIGNING = "WAITING_SIGNING",
    ACTIVE = "ACTIVE",
    COMPLETED = "COMPLETED",
}

export enum RequestStatus {
    WAITING = "WAITING",
    CONFIRMED = "CONFIRMED",
    REJECTION = "REJECTION",
}

export enum ResponseStatus {
    CONFIRMED = "CONFIRMED",
    REJECTION = "REJECTION",
    PROCESSED = "PROCESSED",
}

export enum UserRole {
    ROLE_ADMIN = "ROLE_ADMIN",
    ROLE_MANAGER = "ROLE_MANAGER",
    ROLE_MAIN_MANAGER = "ROLE_MAIN_MANAGER",
}

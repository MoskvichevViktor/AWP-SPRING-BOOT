export interface CreditRequest {
    id: number,
    requesterId: number,
    requesterFullName: string,
    creditSum: number,
    actual: boolean,
    created: Date
}
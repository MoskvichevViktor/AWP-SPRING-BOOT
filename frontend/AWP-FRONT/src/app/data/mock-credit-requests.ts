import { CreditRequest } from "../models/credit-request";

export const REQUESTS: CreditRequest[] = [
    {
        id: 1,
        requesterId: 100,
        requesterFullName: 'Чапурин Потап Максимович',
        creditSum: 500000,
        actual: true,
        created: new Date()
    },
    {
        id: 2,
        requesterId: 99,
        requesterFullName: 'Снежков Михаил Гаврилович',
        creditSum: 78000,
        actual: true,
        created: new Date()
    },
    {
        id: 3,
        requesterId: 40,
        requesterFullName: 'Заплатин Иван Григорьевич',
        creditSum: 83500,
        actual: true,
        created: new Date()
    },
    {
        id: 4,
        requesterId: 205,
        requesterFullName: 'Стуколова Агриппина Семеновна',
        creditSum: 103000,
        actual: true,
        created: new Date()
    },
    {
        id: 5,
        requesterId: 205,
        requesterFullName: 'Смолокуров Марк Данилович',
        creditSum: 211000,
        actual: true,
        created: new Date()
    },
    {
        id: 6,
        requesterId: 100,
        requesterFullName: 'Чапурин Потап Максимович',
        creditSum: 180400,
        actual: true,
        created: new Date()
    },
];
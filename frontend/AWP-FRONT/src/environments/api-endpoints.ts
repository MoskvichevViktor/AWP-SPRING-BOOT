export const ApiEndpoints = {
    auth: {
        login: '/auth',
    },
    calculation: {
        process: '/calculator',
    },
    creditRequests: {
        list: '/credit_requests',
        get: (id: number) => `/credit_requests/${id}`,
        create: '/credit_requests',
        update: '/credit_requests',
    },
    creditResponses: {
        list: '/credit_responses',
        get: (id: number) => `/credit_responses/${id}`,
        create: '/credit_responses',
        update: '/credit_responses',
    },
    contracts: {
        list: '/contracts',
        get: (id: number) => `/contracts/${id}`,
        create: '/contracts',
        update: '/contracts',
    },
    clients: {
        list: '/clients',
        get: (id: number) => `/clients/${id}`,
        create: '/clients',
        update: '/clients',
    },
    users: {
        list: '/users',
        get: (id: number) => `/users/${id}`,
        create: '/users',
        update: '/users',
    },
}

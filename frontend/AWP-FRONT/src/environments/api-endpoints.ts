export const ApiEndpoints = {
    auth: {
        login: '/auth',
    },
    creditRequests: {
        list: '/credit_requests',
        get: (id: number) => `/credit_requests/${id}`,
        create: '/credit_requests',
        update: '/credit_requests',
    },
    creditResponses: {
        list: '/credit_responses',
    },
    contracts: {
        list: '/contracts',
    },
    clients: {
        list: '/clients',
        get: (id: number) => `/clients/${id}`,
    },
    users: {
        list: '/users',
    },
}
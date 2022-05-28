export const ApiEndpoints = {
    auth: {
        login: '/auth',
    },
    creditRequests: {
        list: '/credit_requests',
        get: (id: number) => `/credit_requests/${id}`,
        create: 'requests/create',
        update: (id: number) => `/credit_requests/${id}`,
        delete: (id: number) => `/credit_requests/${id}`,
    },
    creditResponses: {
        list: '/credit_responses',
    },
    contracts: {
        list: '/contracts',
    },
    clients: {
        list: '/clients',
    },
    users: {
        list: '/users',
    },
}
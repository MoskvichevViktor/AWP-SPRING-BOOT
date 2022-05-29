export const ApiEndpoints = {
    auth: {
        login: '/auth',
    },
    creditRequests: {
        list: '/credit_requests',
        get: (id: number) => `/credit_requests/{id}`,
        create: 'requests/create',
        update: (id: number) => `/credit_requests/{id}`,
        delete: (id: number) => `/credit_requests/{id}`,
    },
    clients: {
        list: '/clients/all',
    },
    users: {
        list: '/users/all',
    },
}
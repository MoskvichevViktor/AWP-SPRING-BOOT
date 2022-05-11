import { ApiEndpoints } from "./api-endpoints";


export const environment = {
    production: false,
    api: {
        url: `${window.location.protocol}//${window.location.hostname}:8080`,
        endpoints: ApiEndpoints,
    },
};

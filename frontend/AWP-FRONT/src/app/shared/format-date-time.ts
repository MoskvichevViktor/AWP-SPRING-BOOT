import * as moment from "moment";

export function formatDateTime(date: string) {
    return moment(date).format('DD.MM.YYYY, HH:mm:ss');
}
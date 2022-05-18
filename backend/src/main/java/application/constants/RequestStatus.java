package application.constants;

public enum RequestStatus {
//    CREATED("created"),
    WAITING("waiting"),
    CONFIRMED("confirmed"),
    REJECTION("rejection");


    private final String statusName;

    RequestStatus(String statusName) {
        this.statusName = statusName;
    }

    @Override
    public String toString() {
        return statusName;
    }
}

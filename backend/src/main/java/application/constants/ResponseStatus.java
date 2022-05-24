package application.constants;

public enum ResponseStatus {
    WAITING("waiting"),
    CONFIRMED("confirmed"),
    REJECTION("rejection");


    private final String statusName;

    ResponseStatus(String statusName) {
        this.statusName = statusName;
    }

    @Override
    public String toString() {
        return statusName;
    }
}

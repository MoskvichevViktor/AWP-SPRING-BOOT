package application.constants;

public enum CreditResponseStatus {
    WAITING("waiting"),
    CONFIRMED("confirmed"),
    REJECTION("rejection");


    private final String statusName;

    CreditResponseStatus(String statusName) {
        this.statusName = statusName;
    }

    @Override
    public String toString() {
        return statusName;
    }
}

package application.constants;

public enum ContractStatus {
    ACTIVE("active"),
    COMPLETED("completed");

    public final String contractStatusName;

    ContractStatus(String contractStatusName) {
        this.contractStatusName = contractStatusName;
    }

    @Override
    public String toString() {
        return contractStatusName;
    }
}

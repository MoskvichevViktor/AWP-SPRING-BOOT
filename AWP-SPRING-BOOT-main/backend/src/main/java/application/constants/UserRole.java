package application.constants;

public enum UserRole {
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_MANAGER("ROLE_MANAGER"),
    ROLE_MAIN_MANAGER("ROLE_MAIN_MANAGER");

    private final String userRoleName;

    UserRole(String userRoleName) {
        this.userRoleName = userRoleName;
    }

    @Override
    public String toString() {
        return userRoleName;
    }

}

package id.ac.ui.cs.advprog.admin.enums;

public enum UserRole {
    ADMIN("ADMIN"),
    CUSTOMER("CUSTOMER");

    private final String value;
    UserRole(String value) {
        this.value = value;
    }

    public static boolean contains(String value) {
        for (UserRole role:UserRole.values()) {
            if (role.value.equals(value)) {
                return true;
            }
        }
        return false;
    }
}
package re.edu.intern_management_prj.util.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum RoleEnum {
    ADMIN,
    MENTOR,
    STUDENT;

    @JsonValue
    public String getValue() {
        return this.name();
    }

    @JsonCreator
    public static RoleEnum fromString(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        // Strip quotes if present
        String cleanValue = value.trim().replaceAll("^\"|\"$", "");
        try {
            return RoleEnum.valueOf(cleanValue.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid role value: " + value, e);
        }
    }
}

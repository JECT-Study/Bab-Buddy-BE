package babbuddy.domain.user.domain.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import babbuddy.domain.user.infra.exception.InvalidRoleException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Role {
    MEMBER("ROLE_MEMBER"),
    TRAINER("ROLE_TRAINER"),
    ADMIN("ROLE_ADMIN");
    private final String key;

    Role(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    // Enum 매핑용 메서드
    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static Role from(String key) {
        return Arrays.stream(Role.values())
                .filter(r -> r.getKey().equalsIgnoreCase(key)) // 대소문자 구분 안함
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("등급이 없네요.: " + key));
    }

    public static Role getByValue(String value) {
        for (Role role : Role.values()) {
            if (role.key.equals(value)) {
                return role;
            }
        }
        throw new InvalidRoleException(value + "라는 역할이 존재하지 않습니다.");
    }
}

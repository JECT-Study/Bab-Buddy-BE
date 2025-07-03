package babbuddy.domain.allergy.domain.entity;


import babbuddy.global.infra.exception.error.BabbuddyException;
import babbuddy.global.infra.exception.error.ErrorCode;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Arrays;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum AllergyType {
    NUTS("견과류"),
    SEAFOOD("해산물"),
    DAIRY("유제품"),
    EGG("계란"),
    GLUTEN("글루텐"),
    SOY("콩"),
    FISH("생선"),
    SHELLFISH("조개류"),
    SESAME("참깨"),
    SULFITES("아황산염"),
    MSG("MSG"),
    SENSITIVE_COLOR("인공색소");

    private final String korean;

    AllergyType(String korean) {
        this.korean = korean;
    }

    public String getKorean() {
        return korean;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static AllergyType from(String key) {
        return Arrays.stream(values())
                .filter(a -> a.name().equalsIgnoreCase(key))
                .findFirst()
                .orElseThrow(() -> new BabbuddyException(ErrorCode.ALLERGY_NOT_EXIST, "입력된 값: '" + key + "'"));
    }
}

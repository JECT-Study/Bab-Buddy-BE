package babbuddy.domain.recommend.presentation.dto.page.bookmark;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Category {
    ALL(null),   // ALL ⇒ where 절에서 필터 안 함
    KOREAN("한식"),
    CHINESE("중식"),
    JAPANESE("일식"),
    WESTERN("양식"),
    ETC("기타");

    private final String dbValue;   // 테이블에 저장한 실제 문자

    @JsonCreator // jackson or spring-web이 enum 변환 시 사용
    public static Category from(String input) {
        return Arrays.stream(Category.values())
                .filter(c -> c.name().equalsIgnoreCase(input) || input.equals(c.dbValue))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown category: " + input));
    }
}
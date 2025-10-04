package babbuddy.domain.recommend.presentation.dto.res.recommend;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "추천 음식 예비 응답")
public record RecommendPreparedFoodRes(


        @Schema(description = "추천 음식 이름", example = "삼겹살")
        String foodName

) {
    public static RecommendPreparedFoodRes of(String foodName) {
        return new RecommendPreparedFoodRes(foodName);
    }
}

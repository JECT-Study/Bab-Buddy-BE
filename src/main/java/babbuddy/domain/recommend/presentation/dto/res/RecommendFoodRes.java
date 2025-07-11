package babbuddy.domain.recommend.presentation.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "추천 음식(음식 이름, 음식 소개, 이미지) 응답")
public record RecommendFoodRes(

        @Schema(description = "음식 ID", example = "1")
        Long id,

        @Schema(description = "추천 음식 이름", example = "삼겹살")
        String foodName,
        @Schema(description = "음식 소개", example = " “오늘 앱이 추천한 음식은 삽겹살! 🍊 친구야, 너도 한번 받아볼래?”")
        String foodIntroduce,

        @Schema(description = "추천 음식 이미지", example = "https://i.namu.wiki/i/oFHlYDjoEh8f-cc3lNK9jAemRkbXxNGwUg7XiW5LGS6DF1P2x8GCeNQxbQhVIwtUS1u53YPw-uoyqpmLtrGNJA.webp")
        String foodImageUrl

) {
    public static RecommendFoodRes of(Long id, String foodName, String foodIntroduce, String foodImageUrl) {
        return new RecommendFoodRes(id, foodName, foodIntroduce, foodImageUrl);
    }
}

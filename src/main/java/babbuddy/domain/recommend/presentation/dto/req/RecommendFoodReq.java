package babbuddy.domain.recommend.presentation.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "음식 추천 요청")
public record RecommendFoodReq(

        @Schema(description = "설문조사 1", example = "기름기 없는 담백한 맛")
        @NotBlank(message = "문항 필수 입니다.")
        String survey1,

        @Schema(description = "설문조사 2", example = "집 근처 정겨운 한식당")
        @NotBlank(message = "문항 필수 입니다.")
        String survey2,

        @Schema(description = "설문조사 3", example = "기름진 음식")
        @NotBlank(message = "문항 필수 입니다.")
        String survey3,

        @Schema(description = "주소", example = "인천광역시 부평구 동수로 28")
        @NotBlank(message = "문항 필수 입니다.")
        String address

) {}

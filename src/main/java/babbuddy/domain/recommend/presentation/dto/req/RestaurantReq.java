package babbuddy.domain.recommend.presentation.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
@Schema(description = "즐겨찾기 등록 해제 요청")
public record RestaurantReq(
        @Schema(description = "음식점 pk 즉 id", example = "1")
        @NotBlank(message = "id 필수입니다.")
        Long restaurantId
) {


}

package babbuddy.domain.food.presentation.dto.req.dislike;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "비선호 음식 등록 요청")
public record PostDislikeReq(

        @Schema(description = "비선호 음식 이름", example = "브로콜리")
        @NotBlank(message = "음식 이름은 필수 입니다.")
        String foodName

) {}

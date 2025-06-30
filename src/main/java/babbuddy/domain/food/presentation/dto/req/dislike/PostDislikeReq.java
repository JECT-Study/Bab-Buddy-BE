package babbuddy.domain.food.presentation.dto.req.dislike;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PostDislikeReq(

        @NotBlank(message = "음식 이름은 필수 입니다.")
        String foodName
) {
}

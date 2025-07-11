package babbuddy.domain.dislikefood.presentation.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
@Schema(description = "비선호 음식들 응답")
public record GetDisLikeRes(

        @Schema(description = "비선호 음식 ID", example = "1")
        Long id,

        @Schema(description = "비선호 음식 이름", example = "브로콜리")
        String foodName

) {
    public static GetDisLikeRes of(Long id, String foodName) {
        return new GetDisLikeRes(id, foodName);
    }
}

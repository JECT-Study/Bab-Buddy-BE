package babbuddy.domain.allergy.presentation.dto.req;

import babbuddy.domain.allergy.domain.entity.AllergyType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Schema(description = "알레르기 등록 요청")
public record AllergyPostReq(
        @Schema(description = "사용자가 선택한 알레르기 타입 목록, 영어로 작성해주세요",
                example = "[\"EGG\", \"MILK\"]")
        @NotNull(message = "알레르기 항목은 필수입니다.")
        List<AllergyType> allergyTypes
) {
}

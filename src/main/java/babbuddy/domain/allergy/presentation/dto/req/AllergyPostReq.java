package babbuddy.domain.allergy.presentation.dto.req;

import babbuddy.domain.allergy.domain.entity.AllergyType;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record AllergyPostReq(
        @NotNull(message = "알레르기 항목은 필수입니다.")
        List<AllergyType> allergyTypes
) {
}

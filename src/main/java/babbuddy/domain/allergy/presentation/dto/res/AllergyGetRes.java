package babbuddy.domain.allergy.presentation.dto.res;


import babbuddy.domain.allergy.domain.entity.AllergyType;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "알레르기 조회 응답")
public record AllergyGetRes(
        @Schema(description = "알레르기 타입 (영문 enum 이름)", example = "EGG")
        String allergyType,

        @Schema(description = "알레르기 한글명", example = "달걀")
        String koreanName

) {
    public static AllergyGetRes of(AllergyType allergyType) {
        return new AllergyGetRes(
                allergyType.name(),
                allergyType.getKorean()
        );
    }

}

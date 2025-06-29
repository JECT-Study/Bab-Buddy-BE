package babbuddy.domain.allergy.presentation.dto.res;


import babbuddy.domain.allergy.domain.entity.AllergyType;

public record AllergyGetRes(
        String allergyType, // enum name
        String koreanName   // 한글 표시용
) {
    public static AllergyGetRes of(AllergyType allergyType) {
        return new AllergyGetRes(
                allergyType.name(),
                allergyType.getKorean()
        );
    }

}

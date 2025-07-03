package babbuddy.domain.allergy.application.service;

import babbuddy.domain.allergy.presentation.dto.req.AllergyPostReq;
import babbuddy.domain.allergy.presentation.dto.res.AllergyGetRes;

import java.util.List;

public interface AllergyService {
    List<AllergyGetRes> getAllergy(String userId);

    void postAllergy(AllergyPostReq allergyPostReq, String userId);
}

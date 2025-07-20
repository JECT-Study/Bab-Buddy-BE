package babbuddy.domain.allergy.application.service.impl;

import babbuddy.domain.allergy.application.service.AllergyService;
import babbuddy.domain.allergy.domain.entity.Allergy;
import babbuddy.domain.allergy.domain.entity.AllergyType;
import babbuddy.domain.allergy.domain.repository.AllergyRepository;
import babbuddy.domain.allergy.presentation.dto.req.AllergyPostReq;
import babbuddy.domain.allergy.presentation.dto.res.AllergyGetRes;
import babbuddy.domain.user.domain.entity.User;
import babbuddy.domain.user.domain.repository.UserRepository;
import babbuddy.global.infra.exception.error.BabbuddyException;
import babbuddy.global.infra.exception.error.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class AllergyServiceImpl implements AllergyService {

    private final AllergyRepository allergyRepository;
    private final UserRepository userRepository;

    @Override
    public List<AllergyGetRes> getAllergy(String userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) throw new BabbuddyException(ErrorCode.USER_NOT_EXIST);


        List<Allergy> byAllergies = allergyRepository.findByUser(user);
        List<AllergyGetRes> resList = new ArrayList<>();
        for (Allergy allergy : byAllergies) {
            resList.add(AllergyGetRes.of(allergy.getAllergyType()));
        }

        return resList;
    }

    @Override
    public void postAllergy(AllergyPostReq allergyPostReq, String userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) throw new BabbuddyException(ErrorCode.USER_NOT_EXIST);

        // 알레르기 등록 또는 수정 시, 기존 항목을 모두 삭제하고 새로운 목록으로 전체 갱신, 12개라서 괜찮!
        allergyRepository.deleteByUser(user);

        List<AllergyType> allergyList = allergyPostReq.allergyTypes();

        for (AllergyType allergyType : allergyList) {
            Allergy allergy = Allergy
                    .builder()
                    .user(user)
                    .allergyType(AllergyType.valueOf(allergyType.name()))
                    .build();
            allergyRepository.save(allergy);
        }
    }
}

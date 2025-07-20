package babbuddy.domain.user.application.service;


import babbuddy.domain.user.presentation.dto.req.NicknameReq;
import babbuddy.domain.user.presentation.dto.res.GetUserRes;

public interface UserService {

    void updateNickname(String userId, NicknameReq req);

    GetUserRes getUser(String userId);


    // 온보딩 상태 확인
    boolean isOnboardingCompleted(String userId);

    // 온보딩 완료 처리
    void completeOnboarding(String userId);
}
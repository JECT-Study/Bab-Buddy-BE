package babbuddy.domain.user.application.service.impl;


import babbuddy.domain.user.application.service.UserService;
import babbuddy.domain.user.domain.entity.User;
import babbuddy.domain.user.domain.repository.UserRepository;
import babbuddy.domain.user.presentation.dto.req.NicknameReq;
import babbuddy.domain.user.presentation.dto.res.GetUserRes;
import babbuddy.global.infra.exception.error.BabbuddyException;
import babbuddy.global.infra.exception.error.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public void updateNickname(String userId, NicknameReq req) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) throw new BabbuddyException(ErrorCode.USER_NOT_EXIST);

        user.updateName(req.nickname());
    }

    @Override
    public GetUserRes getUser(String userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) throw new BabbuddyException(ErrorCode.USER_NOT_EXIST);

        return GetUserRes.of(user);
    }

    @Override
    public boolean isOnboardingCompleted(String userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) throw new BabbuddyException(ErrorCode.USER_NOT_EXIST);

        return user.isOnboardingCompleted();
    }

    @Override
    public void completeOnboarding(String userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) throw new BabbuddyException(ErrorCode.USER_NOT_EXIST);

        user.updateCompleteOnboarding();
        log.info("사용자 {}의 온보딩이 완료되었습니다.", userId);
    }
}

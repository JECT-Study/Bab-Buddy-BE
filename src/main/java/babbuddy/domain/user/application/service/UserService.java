package babbuddy.domain.user.application.service;


import babbuddy.domain.user.presentation.dto.req.NicknameReq;

public interface UserService {

    void updateNickname(String userId, NicknameReq req);

}
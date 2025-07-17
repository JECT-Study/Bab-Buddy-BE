package babbuddy.domain.user.application.service;


import babbuddy.domain.user.presentation.dto.req.NicknameReq;
import babbuddy.domain.user.presentation.dto.res.GetUserRes;

public interface UserService {

    void updateNickname(String userId, NicknameReq req);

    GetUserRes getUser(String userId);

}
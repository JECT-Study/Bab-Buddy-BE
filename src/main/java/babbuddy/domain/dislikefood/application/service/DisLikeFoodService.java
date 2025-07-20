package babbuddy.domain.dislikefood.application.service;

import babbuddy.domain.dislikefood.presentation.dto.req.PostDislikeReq;
import babbuddy.domain.dislikefood.presentation.dto.res.GetDisLikeRes;

import java.util.List;

public interface DisLikeFoodService {

    List<GetDisLikeRes> getDisLikeFood(String userId);
    void postDisLikeFood(PostDislikeReq req, String userId);
    void deleteDisLikeFood(Long foodId, String userId);

}

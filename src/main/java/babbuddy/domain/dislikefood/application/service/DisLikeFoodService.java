package babbuddy.domain.dislikefood.application.service;

import babbuddy.domain.dislikefood.presentation.dto.req.PostDislikeReq;
import babbuddy.domain.dislikefood.presentation.dto.res.GetDisLikeRes;
import babbuddy.domain.dislikefood.presentation.dto.res.PostDisLikeRes;

import java.util.List;

public interface DisLikeFoodService {

    List<GetDisLikeRes> getDisLikeFood(String userId);
    PostDisLikeRes postDisLikeFood(PostDislikeReq req, String userId);
    void deleteDisLikeFood(Long foodId, String userId);

}

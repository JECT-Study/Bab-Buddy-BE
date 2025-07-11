package babbuddy.domain.food.application.service;

import babbuddy.domain.food.presentation.dto.dislike.req.PostDislikeReq;
import babbuddy.domain.food.presentation.dto.dislike.res.GetDisLikeRes;

import java.util.List;

public interface DisLikeFoodService {

    List<GetDisLikeRes> getDisLikeFood(String userId);
    void postDisLikeFood(PostDislikeReq req, String userId);
    void deleteDisLikeFood(Long foodId, String userId);

}

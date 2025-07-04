package babbuddy.domain.food.application.service;

import babbuddy.domain.food.presentation.dto.req.dislike.PostDislikeReq;
import babbuddy.domain.food.presentation.dto.res.dislike.GetDisLikeRes;

import java.util.List;

public interface DisLikeFoodService {

    List<GetDisLikeRes> getDisLikeFood(String userId);
    void postDisLikeFood(PostDislikeReq req, String userId);
    void deleteDisLikeFood(Long foodId, String userId);

}

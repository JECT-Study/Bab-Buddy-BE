package babbuddy.domain.recommend.application.service;

import babbuddy.domain.recommend.presentation.dto.req.RecommendFoodReq;
import babbuddy.domain.recommend.presentation.dto.res.RecommendFoodRes;

public interface RecommendFoodService {

    RecommendFoodRes recommendFood(RecommendFoodReq req, String userId);
}

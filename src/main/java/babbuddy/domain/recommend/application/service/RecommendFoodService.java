package babbuddy.domain.recommend.application.service;

import babbuddy.domain.recommend.presentation.dto.req.RecommendFoodReq;
import babbuddy.domain.recommend.presentation.dto.res.recommend.*;

import java.util.List;

public interface RecommendFoodService {

    RecommendFoodRes recommendFood(RecommendFoodReq req, String userId);

    void doRestaurantAsync(String address, RecommendFoodRes res, String city);

    List<RestaurantSelectRes> restaurantAll(Long foodId);

    RecommendAllRes recommendALL(Long foodId);

    List<RecommendPreparedFoodRes> preparedFood(Long foodId);
}

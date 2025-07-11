package babbuddy.domain.recommend.application.service;

import babbuddy.domain.recommend.presentation.dto.req.RecommendFoodReq;
import babbuddy.domain.recommend.presentation.dto.res.RecommendFoodRes;
import babbuddy.domain.recommend.presentation.dto.res.RestaurantRes;

import java.util.List;

public interface RecommendFoodService {

    RecommendFoodRes recommendFood(RecommendFoodReq req, String userId);

    void doRestaurantAsync(String address, RecommendFoodRes res, String city);

    List<RestaurantRes> restaurantAll(Long foodId);
}

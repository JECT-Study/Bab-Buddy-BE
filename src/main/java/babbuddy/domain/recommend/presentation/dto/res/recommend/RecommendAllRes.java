package babbuddy.domain.recommend.presentation.dto.res.recommend;

import java.util.List;

public record RecommendAllRes(
        List<RestaurantSelectRes> restaurantRes,

        RecommendFoodRes foodRes
) {

    public static RecommendAllRes of(List<RestaurantSelectRes> restaurantRes, RecommendFoodRes foodRes) {
        return new RecommendAllRes(restaurantRes, foodRes);
    }
}

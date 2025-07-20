package babbuddy.domain.user.presentation.dto.res;

import babbuddy.domain.recommend.presentation.dto.res.recommend.RestaurantSelectRes;

import java.time.LocalDateTime;
import java.util.List;

public record FoodWithRestaurantsRes(
        String foodName,

        LocalDateTime createAt,
        List<RestaurantSelectRes> restaurantList
) {
    public static FoodWithRestaurantsRes of(String foodName, LocalDateTime createAt, List<RestaurantSelectRes> restaurants) {
        return new FoodWithRestaurantsRes(foodName, createAt, restaurants);
    }
}

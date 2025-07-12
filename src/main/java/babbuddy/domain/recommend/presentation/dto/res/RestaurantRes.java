package babbuddy.domain.recommend.presentation.dto.res;

import babbuddy.domain.recommend.domain.entity.RecommendRestaurant;

public record RestaurantRes(
        String name,
        String restaurantType,
        String address,
        String rating,
        double latitude,
        double longitude
) {
    public static RestaurantRes of(RecommendRestaurant recommendRestaurant) {
        return new RestaurantRes(
                recommendRestaurant.getRestaurantName(),
                recommendRestaurant.getRestaurantType(),
                recommendRestaurant.getAddress(),
                recommendRestaurant.getRate(),
                recommendRestaurant.getLatitude(),
                recommendRestaurant.getLongitude()
        );
    }
}

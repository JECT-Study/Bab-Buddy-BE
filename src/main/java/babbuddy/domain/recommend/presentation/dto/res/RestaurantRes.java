package babbuddy.domain.recommend.presentation.dto.res;

import babbuddy.domain.recommend.domain.entity.RecommendRestaurant;

public record RestaurantRes(
        String name,
        String rating,
        String restaurantType,
        String address,
        Double latitude,
        Double longitude
) {
    public static RestaurantRes of(RecommendRestaurant recommendRestaurant) {
        return new RestaurantRes(
                recommendRestaurant.getRestaurantName(),
                recommendRestaurant.getRate(),
                recommendRestaurant.getRestaurantType(),
                recommendRestaurant.getAddress(),
                recommendRestaurant.getLatitude(),
                recommendRestaurant.getLongitude()
        );
    }
}

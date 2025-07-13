package babbuddy.domain.recommend.presentation.dto.res.recommend;

import babbuddy.domain.recommend.domain.entity.RecommendRestaurant;

public record RestaurantJsonRes(
        String name,
        String restaurantType,
        String address,
        String rating,
        double latitude,
        double longitude
) {
    public static RestaurantJsonRes of(RecommendRestaurant recommendRestaurant) {
        return new RestaurantJsonRes(
                recommendRestaurant.getRestaurantName(),
                recommendRestaurant.getRestaurantType(),
                recommendRestaurant.getAddress(),
                recommendRestaurant.getRate(),
                recommendRestaurant.getLatitude(),
                recommendRestaurant.getLongitude()
        );
    }
}

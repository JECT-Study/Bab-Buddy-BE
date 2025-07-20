package babbuddy.domain.recommend.presentation.dto.res.recommend;

import babbuddy.domain.recommend.domain.entity.RecommendRestaurant;

import java.time.LocalDateTime;

public record RestaurantSelectRes(
        Long id,
        String name,
        String restaurantType,
        String address,
        String rating,
        double latitude,
        double longitude,
        LocalDateTime createdAt
) {
    public static RestaurantSelectRes of(RecommendRestaurant recommendRestaurant) {
        return new RestaurantSelectRes(
                recommendRestaurant.getId(),
                recommendRestaurant.getRestaurantName(),
                recommendRestaurant.getRestaurantType(),
                recommendRestaurant.getAddress(),
                recommendRestaurant.getRate(),
                recommendRestaurant.getLatitude(),
                recommendRestaurant.getLongitude(),
                recommendRestaurant.getCreatedAt()
        );
    }
}

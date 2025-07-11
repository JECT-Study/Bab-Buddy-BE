package babbuddy.domain.recommend.presentation.dto.res;

public record RestaurantRes(
        String name,
        String rating,
        String restaurantType,
        String address,
        Double latitude,
        Double longitude
) {}

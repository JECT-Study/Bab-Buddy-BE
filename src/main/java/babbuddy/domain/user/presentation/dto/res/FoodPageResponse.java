package babbuddy.domain.user.presentation.dto.res;

import org.springframework.data.domain.Page;

import java.util.List;

public record FoodPageResponse(
        List<FoodWithRestaurantsRes> content,
        int totalPages,
        long totalElements,
        int currentPage
) {
    public static FoodPageResponse of(Page<FoodWithRestaurantsRes> page) {
        return new FoodPageResponse(
                page.getContent(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.getNumber()
        );
    }
}


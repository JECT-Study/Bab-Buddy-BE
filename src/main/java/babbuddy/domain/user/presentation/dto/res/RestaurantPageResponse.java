package babbuddy.domain.user.presentation.dto.res;

import babbuddy.domain.recommend.presentation.dto.res.recommend.RestaurantSelectRes;
import org.springframework.data.domain.Page;

import java.util.List;

public record RestaurantPageResponse(
        List<RestaurantSelectRes> content,
        int totalPages,
        long totalElements,
        int currentPage
) {
    public static RestaurantPageResponse of(Page<RestaurantSelectRes> page) {
        return new RestaurantPageResponse(
                page.getContent(),
                page.getTotalPages(),
                page.getTotalElements(),
                page.getNumber()
        );
    }
}

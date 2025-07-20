package babbuddy.domain.user.application.service;

import babbuddy.domain.user.presentation.dto.paging.bookmark.Category;
import babbuddy.domain.user.presentation.dto.paging.bookmark.SortOption;
import babbuddy.domain.user.presentation.dto.req.RestaurantBookmarkReq;

import babbuddy.domain.recommend.presentation.dto.res.recommend.RestaurantSelectRes;
import babbuddy.domain.user.presentation.dto.res.FoodWithRestaurantsRes;
import org.springframework.data.domain.Page;

public interface RestaurantSelectService {

    void updateBookmark(String userId, RestaurantBookmarkReq req);

    Page<RestaurantSelectRes> getBookmarks(
            String userId,
            Category category,
            SortOption sortOption,
            int page,                // 0-based
            int size                 // 기본 10
    );

    Page<FoodWithRestaurantsRes> getGroupBy(
            String userId,
            Category category,
            SortOption sortOption,
            int page,                // 0-based
            int size                 // 기본 10
    );
}

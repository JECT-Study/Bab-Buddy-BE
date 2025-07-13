package babbuddy.domain.recommend.application.service;

import babbuddy.domain.recommend.presentation.dto.page.bookmark.Category;
import babbuddy.domain.recommend.presentation.dto.page.bookmark.SortOption;
import babbuddy.domain.recommend.presentation.dto.req.RestaurantReq;

import babbuddy.domain.recommend.presentation.dto.res.recommend.RestaurantSelectRes;
import org.springframework.data.domain.Page;

public interface RestaurantService {

    void updateBookmark(String userId, RestaurantReq req);

    Page<RestaurantSelectRes> getBookmarks(
            String userId,
            Category category,
            SortOption sortOption,
            int page,                // 0-based
            int size                 // 기본 10
    );
}

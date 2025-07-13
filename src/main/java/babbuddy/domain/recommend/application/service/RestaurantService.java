package babbuddy.domain.recommend.application.service;

import babbuddy.domain.recommend.presentation.dto.req.RestaurantReq;

public interface RestaurantService {

    void updateBookmark(String userId, RestaurantReq req);
}

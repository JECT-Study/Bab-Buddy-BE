package babbuddy.domain.recommend.application.service.impl;

import babbuddy.domain.recommend.application.service.RestaurantService;
import babbuddy.domain.recommend.domain.entity.RecommendRestaurant;
import babbuddy.domain.recommend.domain.repository.RecommendRestaurantRepository;
import babbuddy.domain.recommend.presentation.dto.req.RestaurantReq;
import babbuddy.domain.user.domain.entity.User;
import babbuddy.domain.user.domain.repository.UserRepository;
import babbuddy.global.infra.exception.error.BabbuddyException;
import babbuddy.global.infra.exception.error.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class RestaurantServiceImpl implements RestaurantService {

    private final UserRepository userRepository;

    private final RecommendRestaurantRepository restaurantRepository;

    @Override
    public void updateBookmark(String userId, RestaurantReq req) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) throw new BabbuddyException(ErrorCode.USER_NOT_EXIST);

        RecommendRestaurant restaurant = restaurantRepository.findById(req.restaurantId()).orElse(null);
        if (restaurant == null) throw new BabbuddyException(ErrorCode.RESTAURANT_NOT_EXIST);

        if (restaurant.isFavorite()) restaurant.updateIsFavorite(false);
        else restaurant.updateIsFavorite(true);

    }
}

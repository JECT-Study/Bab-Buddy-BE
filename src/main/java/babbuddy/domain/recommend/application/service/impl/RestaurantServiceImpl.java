package babbuddy.domain.recommend.application.service.impl;

import babbuddy.domain.recommend.application.service.RestaurantService;
import babbuddy.domain.recommend.domain.entity.RecommendFood;
import babbuddy.domain.recommend.domain.entity.RecommendRestaurant;
import babbuddy.domain.recommend.domain.repository.RecommendFoodRepository;
import babbuddy.domain.recommend.domain.repository.RecommendRestaurantRepository;
import babbuddy.domain.recommend.presentation.dto.page.bookmark.Category;
import babbuddy.domain.recommend.presentation.dto.page.bookmark.SortOption;
import babbuddy.domain.recommend.presentation.dto.req.RestaurantReq;
import babbuddy.domain.recommend.presentation.dto.res.recommend.RestaurantSelectRes;
import babbuddy.domain.user.domain.entity.User;
import babbuddy.domain.user.domain.repository.UserRepository;
import babbuddy.global.infra.exception.error.BabbuddyException;
import babbuddy.global.infra.exception.error.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class RestaurantServiceImpl implements RestaurantService {

    private final UserRepository userRepository;

    private final RecommendRestaurantRepository restaurantRepository;

    private final RecommendFoodRepository recommendFoodRepository;
    @Override
    public void updateBookmark(String userId, RestaurantReq req) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) throw new BabbuddyException(ErrorCode.USER_NOT_EXIST);

        RecommendRestaurant restaurant = restaurantRepository.findById(req.restaurantId()).orElse(null);
        if (restaurant == null) throw new BabbuddyException(ErrorCode.RESTAURANT_NOT_EXIST);

        if (restaurant.isFavorite()) restaurant.updateIsFavorite(false);
        else restaurant.updateIsFavorite(true);

    }

    @Override
    public Page<RestaurantSelectRes> getBookmarks(String userId, Category category, SortOption sortOption, int page, int size) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) throw new BabbuddyException(ErrorCode.USER_NOT_EXIST);

        List<RecommendFood> foods = recommendFoodRepository.findAllByUser(user);

        Sort sort = Sort.by("createdAt");
        sort = (sortOption == SortOption.OLDEST)
                ? sort.ascending()
                : sort.descending();   // 기본 최신순

        Pageable pageable = PageRequest.of(page, size, sort);

        log.info(category.getDbValue());
        // 카테고리-별 조회
        Page<RecommendRestaurant> entities;
        if (category == Category.ALL) {
            entities = restaurantRepository
                    .findAllByRecommendFoodInAndFavoriteTrue(foods, pageable);
        } else if (category == Category.ETC) {
            List<String> excludedTypes = List.of("한식", "중식", "일식", "양식");
            entities = restaurantRepository
                    .findAllByRecommendFoodInAndRestaurantTypeNotInAndFavoriteTrue(
                            foods, excludedTypes, pageable);
        } else {
            entities = restaurantRepository
                    .findAllByRecommendFoodInAndRestaurantTypeAndFavoriteTrue(
                            foods, category.getDbValue(), pageable);
        }

        return entities.map(RestaurantSelectRes::of);
    }
}

package babbuddy.domain.user.application.service;

import babbuddy.domain.user.application.RestaurantSelectService;
import babbuddy.domain.recommend.domain.entity.RecommendFood;
import babbuddy.domain.recommend.domain.entity.RecommendRestaurant;
import babbuddy.domain.recommend.domain.repository.RecommendFoodRepository;
import babbuddy.domain.recommend.domain.repository.RecommendRestaurantRepository;
import babbuddy.domain.user.presentation.dto.page.bookmark.Category;
import babbuddy.domain.user.presentation.dto.page.bookmark.SortOption;
import babbuddy.domain.user.presentation.dto.req.RestaurantBookmarkReq;
import babbuddy.domain.recommend.presentation.dto.res.recommend.RestaurantSelectRes;
import babbuddy.domain.user.domain.entity.User;
import babbuddy.domain.user.domain.repository.UserRepository;
import babbuddy.domain.user.presentation.dto.res.FoodWithRestaurantsRes;
import babbuddy.global.infra.exception.error.BabbuddyException;
import babbuddy.global.infra.exception.error.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class RestaurantSelectServiceImpl implements RestaurantSelectService {

    private final UserRepository userRepository;

    private final RecommendRestaurantRepository restaurantRepository;

    private final RecommendFoodRepository recommendFoodRepository;
    @Override
    public void updateBookmark(String userId, RestaurantBookmarkReq req) {
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

    @Override
    public Page<FoodWithRestaurantsRes> getGroupBy(String userId, Category category, SortOption sortOption, int page, int size) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) throw new BabbuddyException(ErrorCode.USER_NOT_EXIST);

        List<RecommendFood> allFoods = recommendFoodRepository.findAllByUser(user);

        // 음식 기준으로 필터링
        List<FoodWithRestaurantsRes> filtered = allFoods.stream()
                .map(food -> {
                    List<RecommendRestaurant> restaurants;

                    // isFavorite 조건 ❌ → 모든 음식점 다 가져오기
                    if (category == Category.ALL) {
                        restaurants = restaurantRepository.findAllByRecommendFood(food);
                    } else if (category == Category.ETC) {
                        List<String> excluded = List.of("한식", "중식", "일식", "양식");
                        restaurants = restaurantRepository
                                .findAllByRecommendFoodAndRestaurantTypeNotIn(food, excluded);
                    } else {
                        restaurants = restaurantRepository
                                .findAllByRecommendFoodAndRestaurantType(food, category.getDbValue());
                    }

                    if (restaurants.isEmpty()) return null;

                    List<RestaurantSelectRes> resList = restaurants.stream()
                            .map(RestaurantSelectRes::of)
                            .toList();

                    return FoodWithRestaurantsRes.of(food.getFoodName(), food.getCreatedAt() ,resList);
                })
                .filter(Objects::nonNull)
                .toList();

        // 음식 기준 페이징
        int total = filtered.size();
        int fromIndex = Math.min(page * size, total);
        int toIndex = Math.min(fromIndex + size, total);
        List<FoodWithRestaurantsRes> pagedList = filtered.subList(fromIndex, toIndex);

        return new PageImpl<>(pagedList, PageRequest.of(page, size), total);
    }
}

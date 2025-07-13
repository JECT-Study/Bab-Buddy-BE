package babbuddy.domain.recommend.domain.repository;


import babbuddy.domain.recommend.domain.entity.RecommendFood;
import babbuddy.domain.recommend.domain.entity.RecommendRestaurant;
import babbuddy.domain.recommend.presentation.dto.res.recommend.RestaurantSelectRes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecommendRestaurantRepository extends JpaRepository<RecommendRestaurant, Long> {
    List<RecommendRestaurant> findByRecommendFood(RecommendFood recommendFood);

    // 카테고리 X
    Page<RecommendRestaurant> findAllByRecommendFoodIn(
            List<RecommendFood> recommendFoods, Pageable pageable);


    // 카테고리 O
    Page<RecommendRestaurant> findAllByRecommendFoodInAndRestaurantType(
            List<RecommendFood> recommendFoods, String restaurantType, Pageable pageable);

    Page<RecommendRestaurant> findAllByRecommendFoodInAndRestaurantTypeNotIn(
            List<RecommendFood> foods, List<String> excludedTypes, Pageable pageable);

}

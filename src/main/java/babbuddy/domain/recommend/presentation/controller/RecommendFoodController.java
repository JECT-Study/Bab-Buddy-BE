package babbuddy.domain.recommend.presentation.controller;

import babbuddy.domain.recommend.application.service.RecommendFoodService;
import babbuddy.domain.recommend.presentation.dto.req.RecommendFoodReq;
import babbuddy.domain.recommend.presentation.dto.res.recommend.RecommendFoodRes;
import babbuddy.domain.recommend.presentation.dto.res.recommend.RestaurantJsonRes;
import babbuddy.domain.recommend.presentation.dto.res.recommend.RestaurantSelectRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommend")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Recommend", description = "추천 관련 API")
public class RecommendFoodController {

    private final RecommendFoodService recommendFoodService;

    @Operation(summary = "음식 추천", description = "사용자 정보 기반으로 음식을 추천합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "음식 추천 성공"),
            @ApiResponse(responseCode = "404", description = "유저 존재하지 않음"),
            @ApiResponse(responseCode = "500", description = "openAI 생성 실패"),
            @ApiResponse(responseCode = "422", description = "JSON 매핑 실패"),
            @ApiResponse(responseCode = "422", description = "이미지 매핑 실패")
    })
    @PostMapping
    public ResponseEntity<RecommendFoodRes> postRecommendFood(
            @RequestBody RecommendFoodReq req,
            @AuthenticationPrincipal String userId) {

        RecommendFoodRes res = recommendFoodService.recommendFood(req, userId);

        // 음식점 추천은 백그라운드에서 수행 (논블로킹)
        recommendFoodService.doRestaurantAsync(req.address(), res, res.category());

        return ResponseEntity.ok(res);
    }

    @Operation(summary = "음식점 조회", description = "추천 음식 ID 기반으로 음식점 3개를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "음식점 3개 조회 성공"),
            @ApiResponse(responseCode = "204", description = "데이터 없음"),
            @ApiResponse(responseCode = "404", description = "음식점 존재하지 않음"),
    })
    @GetMapping("/{foodId}")
    public ResponseEntity<?> getRestaurant(
            @PathVariable Long foodId) {
        List<RestaurantSelectRes> restaurantRes = recommendFoodService.restaurantAll(foodId);
        if (restaurantRes.isEmpty()) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("주변 음식점이 없습니다.");
        return ResponseEntity.ok(restaurantRes);
    }


    /*
    * *
    *   @PostMapping("/test")
    public void test(
            @RequestBody RecommendFoodReq req,
            @AuthenticationPrincipal String userId) {

        // 음식점 추천은 백그라운드에서 수행 (논블로킹)

        recommendFoodService.doRestaurantAsync("경기도 안양시 동안구 시민대로", RecommendFoodRes.of(35L, "삼겹살", "비빔밥", "비빔밥", "비빔밥"), "인천");

    }
    * */
}

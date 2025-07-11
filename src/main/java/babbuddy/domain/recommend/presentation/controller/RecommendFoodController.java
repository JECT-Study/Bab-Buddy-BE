package babbuddy.domain.recommend.presentation.controller;

import babbuddy.domain.recommend.application.service.RecommendFoodService;
import babbuddy.domain.recommend.presentation.dto.req.RecommendFoodReq;
import babbuddy.domain.recommend.presentation.dto.res.RecommendFoodRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
            @ApiResponse(responseCode = "400", description = "유저 존재하지 않음"),
            @ApiResponse(responseCode = "400", description = "openAI 생성 실패")
    })
    @PostMapping
    public ResponseEntity<RecommendFoodRes> postRecommendFood(
            @RequestBody RecommendFoodReq req,
            @AuthenticationPrincipal String userId) {

        RecommendFoodRes res = recommendFoodService.recommendFood(req, userId);

        // 음식점 추천은 백그라운드에서 수행
        recommendFoodService.doRestaurantAsync(req.address(), res);

        return ResponseEntity.ok(res);
    }

}

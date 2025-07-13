package babbuddy.domain.recommend.presentation.controller;

import babbuddy.domain.recommend.application.service.RestaurantService;
import babbuddy.domain.recommend.domain.repository.RecommendFoodRepository;
import babbuddy.domain.recommend.presentation.dto.page.bookmark.Category;
import babbuddy.domain.recommend.presentation.dto.page.bookmark.SortOption;
import babbuddy.domain.recommend.presentation.dto.req.RestaurantReq;

import babbuddy.domain.recommend.presentation.dto.res.recommend.RestaurantSelectRes;
import babbuddy.domain.user.domain.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurant")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Restaurant", description = "음식점 관련 API")
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final UserRepository userRepository;
    private final RecommendFoodRepository recommendFoodRepository;

    @Operation(summary = "음식점 즐겨찾기", description = "음식점을 즐겨찾기 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "음식 추천 성공"),
            @ApiResponse(responseCode = "400", description = "유저 존재하지 않음"),
    })
    @PatchMapping
    public void updateBookmark(
            @RequestBody RestaurantReq req,
            @AuthenticationPrincipal String userId) {

        restaurantService.updateBookmark(userId, req);
    }

//    @Operation(summary = "음식점 조회(마이페이지용 - 추천 결과 히스토리)", description = "사용자가 기록한 모든 음식점들을 조회합니다.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "음식점 조회 성공"),
//            @ApiResponse(responseCode = "400", description = "유저 존재하지 않음"),
//    })
//    @GetMapping
//    public ResponseEntity<List<>> getRestaurantHistoryALL(@AuthenticationPrincipal String userId) {
//
//    }

    @Operation(summary = "음식점 조회(마이페이지용 - 북마크)", description = "사용자가 기록한 모든 음식점들을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "음식점 조회 성공"),
            @ApiResponse(responseCode = "400", description = "유저 존재하지 않음"),
    })
    @GetMapping("/bookmarks")
    public ResponseEntity<List<RestaurantSelectRes>> getRestaurantBookmarks(
            @AuthenticationPrincipal String userId, // userId 꺼내오기
            @RequestParam(defaultValue = "ALL") String category,
            @RequestParam(defaultValue = "LATEST") SortOption order,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Category parsedCategory = Category.from(category);

        log.info(parsedCategory.getDbValue());

        Page<RestaurantSelectRes> result =
                restaurantService.getBookmarks(userId, parsedCategory, order, page, size);
        return ResponseEntity.ok(result.getContent());
    }


}

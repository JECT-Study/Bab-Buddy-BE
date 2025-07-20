package babbuddy.domain.user.presentation.controller;

import babbuddy.domain.user.application.service.RestaurantSelectService;
import babbuddy.domain.user.presentation.dto.paging.bookmark.Category;
import babbuddy.domain.user.presentation.dto.paging.bookmark.SortOption;
import babbuddy.domain.user.presentation.dto.req.RestaurantBookmarkReq;

import babbuddy.domain.recommend.presentation.dto.res.recommend.RestaurantSelectRes;
import babbuddy.domain.user.presentation.dto.res.FoodWithRestaurantsRes;
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
public class RestaurantSelectController {

    private final RestaurantSelectService restaurantService;

    @Operation(summary = "음식점 즐겨찾기", description = "음식점을 즐겨찾기 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "음식 추천 성공"),
            @ApiResponse(responseCode = "404", description = "유저 존재하지 않음"),
            @ApiResponse(responseCode = "404", description = "음식점 정보가 없음"),
    })
    @PatchMapping
    public void updateBookmark(
            @RequestBody RestaurantBookmarkReq req,
            @AuthenticationPrincipal String userId) {

        restaurantService.updateBookmark(userId, req);
    }

    @Operation(summary = "음식점 조회(마이페이지용 - 추천 결과 히스토리)", description = "사용자가 기록한 모든 음식점들을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "음식점 조회 성공"),
            @ApiResponse(responseCode = "404", description = "유저 존재하지 않음 -211"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 카테고리입니다. -405"),
    })
    @GetMapping("/history")
    public ResponseEntity<List<FoodWithRestaurantsRes>> getRestaurantHistoryALL(@AuthenticationPrincipal String userId,
                                                                                @RequestParam(defaultValue = "ALL") String category,
                                                                                @RequestParam(defaultValue = "LATEST") SortOption order,
                                                                                @RequestParam(defaultValue = "0") int page,
                                                                                @RequestParam(defaultValue = "6") int size) {
        Category parsedCategory = Category.from(category);


        Page<FoodWithRestaurantsRes> result = restaurantService.getGroupBy(userId, parsedCategory, order, page, size);
        return ResponseEntity.ok(result.getContent());
    }

    @Operation(summary = "음식점 조회(마이페이지용 - 북마크)", description = "사용자가 기록한 모든 음식점들을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "음식점 조회 성공"),
            @ApiResponse(responseCode = "404", description = "유저 존재하지 않음 -211"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 카테고리입니다. -405"),
    })
    @GetMapping("/bookmarks")
    public ResponseEntity<List<RestaurantSelectRes>> getRestaurantBookmarks(
            @AuthenticationPrincipal String userId,
            @RequestParam(defaultValue = "ALL") String category,
            @RequestParam(defaultValue = "LATEST") SortOption order,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size) {
        Category parsedCategory = Category.from(category);


        Page<RestaurantSelectRes> result =
                restaurantService.getBookmarks(userId, parsedCategory, order, page, size);
        return ResponseEntity.ok(result.getContent());
    }


}

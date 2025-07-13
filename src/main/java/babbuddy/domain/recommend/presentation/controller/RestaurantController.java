package babbuddy.domain.recommend.presentation.controller;

import babbuddy.domain.recommend.application.service.RecommendFoodService;
import babbuddy.domain.recommend.application.service.RestaurantService;
import babbuddy.domain.recommend.presentation.dto.req.RecommendFoodReq;
import babbuddy.domain.recommend.presentation.dto.req.RestaurantReq;
import babbuddy.domain.recommend.presentation.dto.res.RecommendFoodRes;
import babbuddy.domain.recommend.presentation.dto.res.RestaurantRes;
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
@RequestMapping("/api/restaurant")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Restaurant", description = "음식점 관련 API")
public class RestaurantController {

    private final RestaurantService restaurantService;

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

//    @Operation(summary = "음식점 조회(마이페이지용)", description = "사용자가 기록한 모든 음식점들을 조회합니다.")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "음식점 3개 조회 성공"),
//            @ApiResponse(responseCode = "400", description = "유저 존재하지 않음"),
//    })
//    @GetMapping
//    public ResponseEntity<?> getRestaurantALL(@AuthenticationPrincipal String userId) {
//
//    }


}

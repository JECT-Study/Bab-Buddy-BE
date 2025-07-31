package babbuddy.domain.dislikefood.presentation.controller;

import babbuddy.domain.dislikefood.application.service.DisLikeFoodService;
import babbuddy.domain.dislikefood.presentation.dto.req.PostDislikeReq;
import babbuddy.domain.dislikefood.presentation.dto.res.GetDisLikeRes;
import babbuddy.domain.dislikefood.presentation.dto.res.PostDisLikeRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Dislike Food", description = "비선호 음식 관련 API")
public class DisLikeFoodController {

    private final DisLikeFoodService foodService;

    @Operation(summary = "비선호 음식 조회", description = "사용자의 비선호 음식 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "비선호 음식 조회 성공"),
            @ApiResponse(responseCode = "404", description = "유저 존재하지 않음")
    })
    @GetMapping
    public ResponseEntity<List<GetDisLikeRes>> getDisLikeFood(@AuthenticationPrincipal String userId) {
        return ResponseEntity.ok(foodService.getDisLikeFood(userId));
    }

    @Operation(summary = "비선호 음식 등록", description = "사용자의 비선호 음식을 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "비선호 음식 등록 성공"),
            @ApiResponse(responseCode = "404", description = "유저 존재하지 않음"),
            @ApiResponse(responseCode = "423", description = "음식 이름이 중복됩니다.")
    })
    @PostMapping
    public ResponseEntity<PostDisLikeRes> postDisLikeFood(@RequestBody @Valid PostDislikeReq req,
                                                          @AuthenticationPrincipal String userId) {
        return ResponseEntity.ok(foodService.postDisLikeFood(req, userId));
    }

    @Operation(summary = "비선호 음식 삭제", description = "사용자의 비선호 음식 중 하나를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "비선호 음식 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "유저 및 비선호 음식 존재하지 않음")
    })
    @DeleteMapping("/{foodId}")
    public void deleteDisLikeFood(@PathVariable Long foodId,
                                  @AuthenticationPrincipal String userId) {
        foodService.deleteDisLikeFood(foodId, userId);
    }
}

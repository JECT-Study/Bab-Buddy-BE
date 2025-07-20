package babbuddy.domain.user.presentation.controller;

import babbuddy.domain.dislikefood.presentation.dto.res.GetDisLikeRes;
import babbuddy.domain.user.application.service.UserService;
import babbuddy.domain.user.presentation.dto.req.NicknameReq;
import babbuddy.domain.user.presentation.dto.res.GetUserRes;
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

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "User", description = "닉네임 수정 API")
public class UserController {

    private final UserService userService;

    @Operation(summary = "닉넴 수정", description = "사용자 닉네임을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "닉네임 수정 성공"),
            @ApiResponse(responseCode = "400", description = "유저 존재하지 않음")
    })
    @PatchMapping
    public void updateNickname(@RequestBody @Valid NicknameReq req, @AuthenticationPrincipal String userId) {
        userService.updateNickname(userId, req);
    }


    @Operation(summary = "사용자 정보 조회 API ", description = "사용자 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "사용자 조회 성공"),
            @ApiResponse(responseCode = "400", description = "유저 존재하지 않음")
    })
    @GetMapping
    public ResponseEntity<GetUserRes> getUser(@AuthenticationPrincipal String userId) {
        return ResponseEntity.ok(userService.getUser(userId));
    }

    @Operation(summary = "온보딩 상태 확인", description = "사용자의 온보딩 완료 여부를 확인합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "온보딩 상태 조회 성공"),
            @ApiResponse(responseCode = "400", description = "유저 존재하지 않음")
    })
    @GetMapping("/onboarding-status")
    public ResponseEntity<Boolean> getOnboardingStatus(@AuthenticationPrincipal String userId) {
        boolean isCompleted = userService.isOnboardingCompleted(userId);
        return ResponseEntity.ok(isCompleted);
    }

    @Operation(summary = "온보딩 완료 처리", description = "사용자의 온보딩을 완료 상태로 변경합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "온보딩 완료 처리 성공"),
            @ApiResponse(responseCode = "400", description = "유저 존재하지 않음")
    })
    @PostMapping("/complete-onboarding")
    public void completeOnboarding(@AuthenticationPrincipal String userId) {
        userService.completeOnboarding(userId);
    }
}
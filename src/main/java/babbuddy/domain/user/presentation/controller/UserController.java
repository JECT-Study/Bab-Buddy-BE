package babbuddy.domain.user.presentation.controller;

import babbuddy.domain.dislikefood.presentation.dto.res.GetDisLikeRes;
import babbuddy.domain.user.application.service.UserService;
import babbuddy.domain.user.presentation.dto.req.NicknameReq;
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
        userService.updateNickname(userId,req);
    }

}
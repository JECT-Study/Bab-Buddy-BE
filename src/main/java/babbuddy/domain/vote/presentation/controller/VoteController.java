package babbuddy.domain.vote.presentation.controller;


import babbuddy.domain.vote.application.service.VoteService;
import babbuddy.domain.vote.presentation.dto.req.CastVoteReq;
import babbuddy.domain.vote.presentation.dto.response.CastVoteRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping ("/api/vote")
public class VoteController {
    private final VoteService voteService;

    /**
     * 투표 등록
     * @param req  투표 등록 요청 본문 (voteRoomId, menuId 포함)
     * @param user
     * @return
     */
    @Operation(summary = "투표 등록", description = "투표 등록 API.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "투표 등록 성공"),
            @ApiResponse(responseCode = "400", description = "투표 잘못 등록 했을 경우"),
            @ApiResponse(responseCode = "404", description = "유저 없음")
    })
    @PostMapping("/register")
    public ResponseEntity<CastVoteRes> castVote(@RequestBody CastVoteReq req,
                                                @AuthenticationPrincipal String user) {
        return ResponseEntity.ok(voteService.castVote(user, req.voteRoomId(), req.menuId()));
    }

    /**
     * 투표 취소
     * @param voteId
     * @param user
     * @return
     */
    @Operation(summary = "투표 취소", description = "투표 취소 API.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "투표 취소 성공"),
            @ApiResponse(responseCode = "400", description = "투표 잘못 취소 했을 경우"),
            @ApiResponse(responseCode = "404", description = "유저 없음")
    })
    @DeleteMapping("/cancel")
    public ResponseEntity<Void> cancelVote(@RequestParam String voteId,
                                           @AuthenticationPrincipal String user) {
        voteService.cancelVote(user, voteId);
        return ResponseEntity.ok().build();
    }
}

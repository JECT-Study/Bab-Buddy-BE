package babbuddy.domain.voteroom.presentation.controller;

import babbuddy.domain.vote.presentation.dto.response.VoteRoomResultResponseDto;
import babbuddy.domain.voteroom.application.service.VoteRoomService;
import babbuddy.domain.voteroom.presentation.dto.request.VoteRoomRequestDto;
import babbuddy.domain.voteroom.presentation.dto.response.VoteRoomDetailResponseDto;
import babbuddy.domain.voteroom.presentation.dto.response.VoteRoomListResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping ("/api/voterooms")
@Tag(name = "Voterooms", description = "투표방 관련 API")
public class VoteRoomController {
    private final VoteRoomService voteRoomService;

    /**
     * 투표방 생성
     * @return
     */
    @Operation(summary = "투표방 생성", description = "투표 방을 생성하는 API.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "투표방 생성 성공"),
            @ApiResponse(responseCode = "400", description = "잘못 생성했을 경우"),
            @ApiResponse(responseCode = "404", description = "유저 없음")
    })
    @PostMapping("/createroom")
    public ResponseEntity<String> createRoom(@RequestBody VoteRoomRequestDto title) {
        String roomId = voteRoomService.createVoteRoom(title.getTitle());
        return ResponseEntity.ok(roomId);
    }
    /**
     * 투표방 종료
     * @param roomId
     * @return
     */
    @Operation(summary = "투표방 종료", description = "투표 방을 종료하는 API.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "투표방 종료 성공"),
            @ApiResponse(responseCode = "400", description = "잘못 종료했을 경우"),
            @ApiResponse(responseCode = "404", description = "유저 없음")
    })
    @PatchMapping("/close/{roomId}")
    public ResponseEntity<Void> closeRoom(@PathVariable String roomId) {
        voteRoomService.closeVoteRoom(roomId);
        return ResponseEntity.ok().build();
    }

    /**
     * 투표방 삭제
     * @param roomId
     * @return
     */
    @Operation(summary = "투표방 삭제", description = "투표 방을 삭제하는 API.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "투표방 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "잘못 삭제했을 경우"),
            @ApiResponse(responseCode = "404", description = "유저 없음")
    })
    @PatchMapping("/delete/{roomId}")
    public ResponseEntity<Void> deleteRoom(@PathVariable String roomId) {
        voteRoomService.deleteVoteRoom(roomId);
        return ResponseEntity.ok().build();
    }

    // TODO : 페이징 개선
    /**
     * 투표방 리스트 조회
     * @return
     */
    @Operation(summary = "투표방 리스트", description = "투표방 리스트를 조회 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "투표방 리스트 조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못 조회했을 경우"),
            @ApiResponse(responseCode = "404", description = "유저 없음")
    })
    @GetMapping
    public ResponseEntity<List<VoteRoomListResponseDto>> getVoteRoomList() {
        List<VoteRoomListResponseDto> rooms = voteRoomService.getVoteRoomList();
        return ResponseEntity.ok(rooms);
    }
    /**
     * 투표방 상세조회
     */
    @Operation(summary = "투표방 상세 조회", description = "메뉴, 참여자, 투표 진행 상황 포함한 상세정보 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "투표방 상세페이지 조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못 조회했을 경우"),
            @ApiResponse(responseCode = "404", description = "유저 없음")
    })
    @GetMapping("/{roomId}")
    public ResponseEntity<VoteRoomDetailResponseDto> getVoteRoomDetail(
            @PathVariable String roomId
    ) {
        VoteRoomDetailResponseDto detail = voteRoomService.getVoteRoomDetail(roomId);
        return ResponseEntity.ok(detail);
    }

    /**
     * 투표방 결과 조회
     * @param roomId
     * @return
     */
    @Operation(summary = "투표방 결과", description = "투표방 결과 API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "투표방 결과 조회 성공"),
            @ApiResponse(responseCode = "400", description = "잘못 조회했을 경우"),
            @ApiResponse(responseCode = "404", description = "유저 없음")
    })
    @GetMapping("/result/{roomId}")
    public ResponseEntity<VoteRoomResultResponseDto> getResult(@PathVariable String roomId) {
        return ResponseEntity.ok(voteRoomService.getVoteRoomFinalResult(roomId));
    }

}

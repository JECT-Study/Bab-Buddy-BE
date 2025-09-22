package babbuddy.domain.voteroom.application.service;

import babbuddy.domain.vote.presentation.dto.response.VoteResultDto;
import babbuddy.domain.vote.presentation.dto.response.VoteRoomResultResponseDto;
import babbuddy.domain.voteroom.presentation.dto.response.VoteRoomDetailResponseDto;
import babbuddy.domain.voteroom.presentation.dto.response.VoteRoomListResponseDto;

import java.util.List;

public interface VoteRoomService {
    /**
     * 투표방을 생성합니다.
     *
     * @param title 투표방 생성 요청 데이터 (제목 등)
     * @return 생성된 투표방의 ID
     */
    String createVoteRoom(String title);

    /**
     * 투표방을 종료합니다.
     * 상태를 FINISHED로 변경합니다.
     *
     * @param roomId 종료할 투표방 ID
     */
    void closeVoteRoom(String roomId);

    /**
     * 투표방을 삭제합니다.
     *
     * @param roomId 삭제할 투표방 ID
     */
    void deleteVoteRoom(String roomId);

    /**
     * 모든 투표방 리스트를 조회합니다.
     * 제목과 참여자 수를 포함한 간단한 정보 제공
     *
     * @return 투표방 목록 DTO 리스트
     */
    List<VoteRoomListResponseDto> getVoteRoomList();

    /**
     * 투표방의 상세 정보를 조회합니다.
     * 메뉴 리스트, 참여자 정보, 투표 완료 수 등 포함
     *
     * @param roomId 조회할 투표방 ID
     * @return 투표방 상세 DTO
     */
    VoteRoomDetailResponseDto getVoteRoomDetail(String roomId);

    /**
     * 투표가 종료된 투표방의 결과를 조회합니다.
     * 상위 1~3위까지 랭킹 및 공동 순위 결과 포함
     *
     * @param voteRoomId 결과를 조회할 투표방 ID
     * @return 최종 결과 응답 DTO
     */
    VoteRoomResultResponseDto getVoteRoomFinalResult(String voteRoomId);

    /**
     * 투표 결과 데이터를 기반으로 메뉴 랭킹을 계산합니다.
     * 내부 로직용이며 공동 순위 및 상위 3위까지만 반환
     *
     * @param voteRoomId 랭킹 계산할 투표방 ID
     * @return 랭킹 결과 DTO
     */
    VoteResultDto calculateRankedResult(String voteRoomId);
}

package babbuddy.domain.vote.application.service.impl;

import babbuddy.domain.menu.domain.entity.Menu;
import babbuddy.domain.menu.domain.repository.MenuRepository;
import babbuddy.domain.user.domain.entity.User;
import babbuddy.domain.user.domain.repository.UserRepository;
import babbuddy.domain.vote.application.service.VoteService;
import babbuddy.domain.vote.domain.entity.Vote;
import babbuddy.domain.vote.domain.repository.VoteRepository;
import babbuddy.domain.voteroom.domain.entity.VoteRoom;
import babbuddy.domain.voteroom.domain.repository.VoteRoomRepository;
import babbuddy.global.infra.exception.error.BabbuddyException;
import babbuddy.global.infra.exception.error.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;
    private final UserRepository userRepository;
    private final VoteRoomRepository voteRoomRepository;
    private final MenuRepository menuRepository;

    /**
     * 투표등록
     * @param userId
     * @param voteRoomId
     * @param menuId
     */
    @Override
    public String castVote(String userId, String voteRoomId, String menuId) {
        if (voteRepository.existsByUserIdAndVoteRoomId(userId, voteRoomId)) {
            log.error("이미 투표한 유저: userId={}, voteRoomId={}", userId, voteRoomId);
            throw new BabbuddyException(ErrorCode.ALREADY_VOTED);
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("유저 조회 실패: userId={}", userId);
                    return new BabbuddyException(ErrorCode.USER_NOT_FOUND);
                });

        VoteRoom room = voteRoomRepository.findById(voteRoomId)
                .orElseThrow(() -> {
                    log.error("투표방 조회 실패: voteRoomId={}", voteRoomId);
                    return new BabbuddyException(ErrorCode.ROOM_NOT_FOUND);
                });

        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> {
                    log.error("메뉴 조회 실패: menuId={}", menuId);
                    return new BabbuddyException(ErrorCode.MENU_NOT_FOUND);
                });

        Vote vote = Vote.builder()
                .user(user)
                .voteRoom(room)
                .menu(menu)
                .build();

        voteRepository.save(vote);

        return vote.getId();
    }

    /**
     * 투표취소
     * @param userId
     * @param voteId
     */
    @Override
    public void cancelVote(String userId, String voteId) {
        Vote vote = voteRepository.findById(voteId)
                .orElseThrow(() -> {
                    log.error("투표 조회 실패 : voteId={}", voteId);
                    return new BabbuddyException(ErrorCode.VOTE_NOT_FOUND); // 투표 없음
                });

        String ownerId = vote.getUser().getId();
        if (!ownerId.equals(userId)) {
            throw new BabbuddyException(ErrorCode.VOTE_FORBIDDEN); // 내 투표가 아님
        }

        voteRepository.delete(vote);
    }


}

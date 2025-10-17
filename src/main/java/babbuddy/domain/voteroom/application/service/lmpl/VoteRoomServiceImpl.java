package babbuddy.domain.voteroom.application.service.lmpl;

import babbuddy.domain.menu.application.service.MenuService;
import babbuddy.domain.menu.presentation.dto.response.MenuResponseDto;
import babbuddy.domain.user.domain.entity.User;
import babbuddy.domain.user.domain.repository.UserRepository;
import babbuddy.domain.vote.domain.repository.VoteRepository;
import babbuddy.domain.vote.presentation.dto.response.MenuInfoDto;
import babbuddy.domain.vote.presentation.dto.response.RankedMenuGroupDto;
import babbuddy.domain.vote.presentation.dto.response.VoteResultDto;
import babbuddy.domain.vote.presentation.dto.response.VoteRoomResultResponseDto;
import babbuddy.domain.voteroom.application.service.VoteRoomService;
import babbuddy.domain.voteroom.domain.entity.VoteRoom;
import babbuddy.domain.voteroom.domain.entity.VoteRoomDislikeFood;
import babbuddy.domain.voteroom.domain.entity.VoteStatus;
import babbuddy.domain.voteroom.domain.repository.VoteRoomDislikeFoodRepository;
import babbuddy.domain.voteroom.domain.repository.VoteRoomRepository;
import babbuddy.domain.voteroom.presentation.dto.request.VoteRoomDislikeRequestDto;
import babbuddy.domain.voteroom.presentation.dto.request.VoteRoomRequestDto;
import babbuddy.domain.voteroom.presentation.dto.response.VoteRoomDetailResponseDto;
import babbuddy.domain.voteroom.presentation.dto.response.VoteRoomListResponseDto;
import babbuddy.domain.voteroom.presentation.dto.response.VoteRoomUser;
import babbuddy.global.infra.exception.error.BabbuddyException;
import babbuddy.global.infra.exception.error.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class VoteRoomServiceImpl implements VoteRoomService {

    private final VoteRoomRepository voteRoomRepository;
    private final VoteRepository voteRepository;
    private final MenuService menuService;
    private final UserRepository userRepository;
    private final VoteRoomDislikeFoodRepository voteRoomDislikeFoodRepository;

    @Override
    public String createVoteRoom(VoteRoomRequestDto dto, String userId) {

        User user = userRepository.findById(userId).orElse(null);
        List<User> participants = new ArrayList<>();
        participants.add(user);
        VoteRoom voteRoom = VoteRoom.builder()
                .title(dto.getTitle())
                .votestatus(VoteStatus.ONGOING)
                .menuSelectMethod(dto.getMenuSelectMethod())
                .user(user)
                .participants(participants)
                .build();
        VoteRoom saved = voteRoomRepository.save(voteRoom);
        log.info("투표방 생성 완료: roomId={}, title={}", saved.getId(), saved.getTitle());
        return saved.getId();
    }

    @Override
    public void closeVoteRoom(String roomId) {
        VoteRoom room = voteRoomRepository.findById(roomId)
                .orElseThrow(() -> {
                    log.error("투표방 조회 실패(종료 시도): roomId={}", roomId);
                    return new BabbuddyException(ErrorCode.ROOM_NOT_FOUND);
                });

        room.changeStatus(VoteStatus.FINISHED);
        log.info("투표방 종료 완료: roomId={}", roomId);
    }

    @Override
    public void deleteVoteRoom(String roomId) {
        VoteRoom room = voteRoomRepository.findById(roomId)
                .orElseThrow(() -> {
                    log.error("투표방 조회 실패(삭제 시도): roomId={}", roomId);
                    return new BabbuddyException(ErrorCode.ROOM_NOT_FOUND);
                });

        voteRoomRepository.delete(room);
        log.info("투표방 삭제 완료: roomId={}", roomId);
    }

    @Override
    public List<VoteRoomListResponseDto> getVoteRoomList(String userId) {
        List<VoteRoom> rooms = voteRoomRepository.findAll();


        return rooms.stream()
                .map(room -> {
                    Boolean isHostUser = room.getUser().getId().equals(userId);

                    return new VoteRoomListResponseDto(
                            room.getId(),
                            room.getTitle(),
                            room.getVotestatus(),
                            room.getTotalParticipantCount(),
                            isHostUser

                    );
                })
                .toList();
    }

    @Override
    public VoteRoomDetailResponseDto getVoteRoomDetail(String roomId, String userId) {
        VoteRoom room = voteRoomRepository.findById(roomId)
                .orElseThrow(() -> new BabbuddyException(ErrorCode.ROOM_NOT_FOUND));

        // 1. 메뉴 리스트 조회
        List<MenuResponseDto> menuList = menuService.getMenus(roomId);

        // 2. 참여자 리스트 조회
        List<User> participants = room.getParticipants();
        List<VoteRoomUser> participantDtos = participants.stream()
                .map(user -> new VoteRoomUser(user.getId(), user.getName(), user.getProfile()))
                .toList();

        // 3. 투표한 인원 수
        int votedCount = voteRepository.countDistinctUsersByRoomId(roomId);

        // 4. 본인이 투표한 메뉴 이름
        String votedMenuName = voteRepository.findVotedMenuNameByUserIdAndRoomId(userId, roomId);


        return new VoteRoomDetailResponseDto(
                room.getId(),
                room.getTitle(),
                String.valueOf(room.getVotestatus()).toUpperCase(),
                menuList,
                participantDtos,
                room.getTotalParticipantCount(),
                votedCount,
                room.getUser().getId().equals(userId),
                room.getMenuSelectMethod(),
                votedMenuName
        );
    }

    @Override
    public VoteRoomResultResponseDto getVoteRoomFinalResult(String voteRoomId) {
        VoteRoom room = voteRoomRepository.findById(voteRoomId)
                .orElseThrow(() -> new BabbuddyException(ErrorCode.ROOM_NOT_FOUND));

        if (room.getVotestatus() != VoteStatus.FINISHED) {
            throw new BabbuddyException(ErrorCode.ROOM_NOT_CLOSED);
        }

        VoteResultDto result = calculateRankedResult(voteRoomId); // 방금 정리한 랭킹 계산 로직

        return new VoteRoomResultResponseDto(room.getId(), room.getTitle(), result, room.getMenuSelectMethod());
    }

    @Override
    public VoteResultDto calculateRankedResult(String voteRoomId) {
        List<Object[]> results = voteRepository.countVotesGroupByMenu(voteRoomId);

        List<MenuInfoDto> sortedMenus = results.stream()
                .map(row -> new MenuInfoDto((String) row[0], ((Number) row[1]).intValue()))
                .sorted((a, b) -> b.getVoteCount() - a.getVoteCount())
                .toList();

        List<RankedMenuGroupDto> rankedTop3 = new ArrayList<>();
        int currentRank = 1;
        int lastVoteCount = -1;
        int groupCount = 0;

        for (MenuInfoDto dto : sortedMenus) {
            if (rankedTop3.size() >= 3 && dto.getVoteCount() != lastVoteCount) {
                break;
            }

            if (dto.getVoteCount() != lastVoteCount) {
                currentRank += groupCount;
                groupCount = 1;

                List<MenuInfoDto> group = new ArrayList<>();
                group.add(dto);
                rankedTop3.add(new RankedMenuGroupDto(currentRank, 1, group));
            } else {
                RankedMenuGroupDto lastGroup = rankedTop3.get(rankedTop3.size() - 1);
                lastGroup.getMenus().add(dto);
                lastGroup.setCount(lastGroup.getCount() + 1);
                groupCount++;
            }

            lastVoteCount = dto.getVoteCount();
        }

        return new VoteResultDto(rankedTop3);
    }

    @Override
    public void postDislikeFood(VoteRoomDislikeRequestDto req, String userId) {
        VoteRoom room = voteRoomRepository.findById(req.getRoomId())
                .orElseThrow(() -> new BabbuddyException(ErrorCode.ROOM_NOT_FOUND));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BabbuddyException(ErrorCode.USER_NOT_FOUND));

        VoteRoomDislikeFood voteRoomDislikeFood = VoteRoomDislikeFood.builder()
                .foodName(req.getName())
                .voteRoom(room)
                .createdBy(user)
                .build();

        voteRoomDislikeFoodRepository.save(voteRoomDislikeFood);
    }

    @Override
    public List<MenuResponseDto> getDislikedFoods(String roomId) {
        VoteRoom room = voteRoomRepository.findById(roomId)
                .orElseThrow(() -> new BabbuddyException(ErrorCode.ROOM_NOT_FOUND));
        
        List<VoteRoomDislikeFood> dislikedFoods = voteRoomDislikeFoodRepository.findByVoteRoom(room);
        
        return dislikedFoods.stream()
                .map(food -> new MenuResponseDto(
                        String.valueOf(food.getId()), 
                        food.getFoodName(),
                        food.getCreatedBy().getId()
                ))
                .toList();
    }

    @Override
     public void deleteDislikedFood(VoteRoomDislikeRequestDto req) {
        VoteRoom room = voteRoomRepository.findById(req.getRoomId())
                .orElseThrow(() -> new BabbuddyException(ErrorCode.ROOM_NOT_FOUND));

        VoteRoomDislikeFood dislikeFood = voteRoomDislikeFoodRepository
                .findByVoteRoomAndFoodName(room, req.getName())
                .orElseThrow(() -> new BabbuddyException(ErrorCode.FOOD_NOT_EXIST));

        voteRoomDislikeFoodRepository.delete(dislikeFood);
    }

    @Override
    public void joinVoteRoom(String roomId, String userId) {
        VoteRoom room = voteRoomRepository.findById(roomId)
                .orElseThrow(() -> new BabbuddyException(ErrorCode.ROOM_NOT_FOUND));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BabbuddyException(ErrorCode.USER_NOT_FOUND));

        room.addParticipant(user);
        voteRoomRepository.save(room);
        log.info("투표방 참여 완료: roomId={}, userId={}", roomId, userId);
    }

    @Override
    public void leaveVoteRoom(String roomId, String userId) {
        VoteRoom room = voteRoomRepository.findById(roomId)
                .orElseThrow(() -> new BabbuddyException(ErrorCode.ROOM_NOT_FOUND));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BabbuddyException(ErrorCode.USER_NOT_FOUND));

        room.removeParticipant(user);
        voteRoomRepository.save(room);
        log.info("투표방 탈퇴 완료: roomId={}, userId={}", roomId, userId);
    }
}

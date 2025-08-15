package babbuddy.domain.menu.application.service.impl;

import babbuddy.domain.menu.application.service.MenuService;
import babbuddy.domain.menu.domain.entity.Menu;
import babbuddy.domain.menu.domain.repository.MenuRepository;
import babbuddy.domain.menu.presentation.dto.reqeust.MenuRequestCreateDto;
import babbuddy.domain.menu.presentation.dto.response.MenuResponseDto;
import babbuddy.domain.user.domain.entity.User;
import babbuddy.domain.user.domain.repository.UserRepository;
import babbuddy.domain.voteroom.domain.entity.VoteRoom;
import babbuddy.domain.voteroom.domain.repository.VoteRoomRepository;
import babbuddy.global.infra.exception.error.BabbuddyException;
import babbuddy.global.infra.exception.error.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final VoteRoomRepository voteRoomRepository;
    private final UserRepository userRepository;

    @Override
    public String registerMenu(MenuRequestCreateDto dto, String userId) {
        VoteRoom room = voteRoomRepository.findById(dto.getVoteRoomId())
                .orElseThrow(() -> {
                    log.error("투표방 조회 실패: voteRoomId={}", dto.getVoteRoomId());
                    return new BabbuddyException(ErrorCode.ROOM_NOT_FOUND);
                });

        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("유저 조회 실패: userId={}", userId);
                    return new BabbuddyException(ErrorCode.USER_NOT_FOUND);
                });

        Menu menu = Menu.builder()
                .name(dto.getName())
                .voteRoom(room)
                .createdBy(user)
                .build();

        menuRepository.save(menu);
        return menu.getId();
    }

    @Override
    public void deleteMenu(String menuId, String userId) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> {
                    log.error("메뉴 조회 실패: menuId={}", menuId);
                    return new BabbuddyException(ErrorCode.MENU_NOT_FOUND);
                });

        if (!menu.getCreatedBy().getId().equals(userId)) {
            log.error("메뉴 삭제 권한 없음: 요청자={}, 등록자={}", userId, menu.getCreatedBy().getId());
            throw new BabbuddyException(ErrorCode.FORBIDDEN_MENU_DELETE);
        }
        menuRepository.delete(menu);
    }

    @Override
    public String updateMenu(String menuId, String newName, String userId) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> {
                    log.error("메뉴 조회 실패: menuId={}", menuId);
                    return new BabbuddyException(ErrorCode.MENU_NOT_FOUND);
                });

        if (!menu.getCreatedBy().getId().equals(userId)) {
            log.error("메뉴 수정 권한 없음: 요청자={}, 등록자={}", userId, menu.getCreatedBy().getId());
            throw new BabbuddyException(ErrorCode.FORBIDDEN_MENU_UPDATE);
        }

        menu.changeName(newName);
        log.info("메뉴 이름 수정 완료: menuId={}, newName={}", menuId, newName);

        return menuId;
    }

    @Override
    public List<MenuResponseDto> getMenus(String voteRoomId) {
        List<Menu> menus = menuRepository.findAllById(voteRoomId);

        return menus.stream()
                .map(menu -> new MenuResponseDto(
                        menu.getId(),
                        menu.getName(),
                        menu.getCreatedBy().getName()
                ))
                .toList();
    }
}

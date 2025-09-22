package babbuddy.domain.menu.application.service;


import babbuddy.domain.menu.presentation.dto.reqeust.MenuRequestCreateDto;
import babbuddy.domain.menu.presentation.dto.response.MenuResponseDto;

import java.util.List;

public interface MenuService{
    String registerMenu(MenuRequestCreateDto dto, String userId);
    void deleteMenu(String menuId, String userId);
    String updateMenu(String menuId, String newName, String userId);
    List<MenuResponseDto> getMenus(String voteRoomId);
}

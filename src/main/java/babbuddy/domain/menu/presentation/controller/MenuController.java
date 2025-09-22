package babbuddy.domain.menu.presentation.controller;

import babbuddy.domain.menu.application.service.MenuService;
import babbuddy.domain.menu.presentation.dto.reqeust.MenuRequestCreateDto;
import babbuddy.domain.menu.presentation.dto.reqeust.MenuRequstUpdateDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping ("/api/menu")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Menu", description = "메뉴 관련 API")
public class MenuController {

    private final MenuService menuService;

    @Operation(summary = "메뉴등록", description = "메뉴 등록입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "메뉴 등록 성공"),
            @ApiResponse(responseCode = "400", description = "잘못 등록했을 경우"),
            @ApiResponse(responseCode = "404", description = "유저 없음")
    })
    @PostMapping("/create")
    public ResponseEntity<String> registerMenu(
            @RequestBody MenuRequestCreateDto dto,
            @AuthenticationPrincipal String userId
    ) {
       String menuId =  menuService.registerMenu(dto, userId);
        return ResponseEntity.ok(menuId);
    }

    @Operation(summary = "메뉴수정", description = "메뉴 수정입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "메뉴 수정 성공"),
            @ApiResponse(responseCode = "400", description = "잘못 수정했을 경우"),
            @ApiResponse(responseCode = "404", description = "유저 없음")
    })
    @PatchMapping("/update/{menuId}")
    public ResponseEntity<String> updateMenu(
            @PathVariable String menuId,
            @RequestBody MenuRequstUpdateDto newName,
            @AuthenticationPrincipal String userId
    ) {
       String menuUpdateId =  menuService.updateMenu(menuId, newName.getName(), userId);
        return ResponseEntity.ok(menuUpdateId);
    }

    @Operation(summary = "메뉴삭제", description = "메뉴 삭제입니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "메뉴 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "잘못 삭제했을 경우"),
            @ApiResponse(responseCode = "404", description = "유저 없음")
    })
    @DeleteMapping("/delete/{menuId}")
    public ResponseEntity<Void> deleteMenu(
            @PathVariable String menuId,
            @AuthenticationPrincipal String userId
    ) {
        menuService.deleteMenu(menuId, userId);
        return ResponseEntity.ok().build();
    }
}

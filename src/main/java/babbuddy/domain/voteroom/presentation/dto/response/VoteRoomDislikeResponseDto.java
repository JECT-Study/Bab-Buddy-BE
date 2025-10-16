package babbuddy.domain.voteroom.presentation.dto.response;

import babbuddy.domain.menu.presentation.dto.response.MenuResponseDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VoteRoomDislikeResponseDto {
    private List<MenuResponseDto> menuList;
}

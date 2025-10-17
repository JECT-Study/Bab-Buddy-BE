package babbuddy.domain.voteroom.presentation.dto.response;

import babbuddy.domain.menu.presentation.dto.response.MenuResponseDto;
import babbuddy.domain.voteroom.domain.entity.MenuSelectMethod;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteRoomRouletteDetailResponseDto {
    private String roomId;
    private String title;
    private String voteStatus;
    private List<MenuResponseDto> menuList;
    private List<VoteRoomUser> participantList;
    private int totalParticipants;
    private boolean isHostUser;
    private MenuSelectMethod menuSelectMethod;
    private String selectedMenuName;
}

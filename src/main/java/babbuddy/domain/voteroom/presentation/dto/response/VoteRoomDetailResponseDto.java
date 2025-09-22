package babbuddy.domain.voteroom.presentation.dto.response;

import babbuddy.domain.menu.presentation.dto.response.MenuResponseDto;
import babbuddy.domain.voteroom.domain.entity.VoteStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteRoomDetailResponseDto {
    private String roomId;
    private String title;
    private String voteStatus;
    private List<MenuResponseDto> menuList;
    private List<VoteRoomUser> participantList;
    private int totalParticipants;
    private int votedParticipants;
}

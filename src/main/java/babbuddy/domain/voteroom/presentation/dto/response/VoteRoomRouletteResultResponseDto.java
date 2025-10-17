package babbuddy.domain.voteroom.presentation.dto.response;

import babbuddy.domain.voteroom.domain.entity.MenuSelectMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteRoomRouletteResultResponseDto {
    private String voteRoomId;
    private String title;
    private String selectedMenuName;
    private MenuSelectMethod menuSelectMethod;
}

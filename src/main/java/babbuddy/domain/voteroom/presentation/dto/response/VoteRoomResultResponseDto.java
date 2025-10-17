package babbuddy.domain.voteroom.presentation.dto.response;

import babbuddy.domain.vote.presentation.dto.response.VoteResultDto;
import babbuddy.domain.voteroom.domain.entity.MenuSelectMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteRoomResultResponseDto {
    private String voteRoomId;
    private String title;
    private VoteResultDto result;
    private MenuSelectMethod menuSelectMethod;
}

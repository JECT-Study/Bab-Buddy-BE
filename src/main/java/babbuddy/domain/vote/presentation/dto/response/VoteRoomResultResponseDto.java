package babbuddy.domain.vote.presentation.dto.response;

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
}

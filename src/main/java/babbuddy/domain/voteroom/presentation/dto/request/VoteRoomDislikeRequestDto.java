package babbuddy.domain.voteroom.presentation.dto.request;


import babbuddy.domain.voteroom.domain.entity.VoteStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VoteRoomDislikeRequestDto {
    private String roomId;

    private String name;
}

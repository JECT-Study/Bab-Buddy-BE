package babbuddy.domain.voteroom.presentation.dto.response;

import babbuddy.domain.voteroom.domain.entity.VoteStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 투표 리스트
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteRoomListResponseDto {
    private String roomId;
    private String title;
    private VoteStatus voteStatus;
    private int participantCount;
}

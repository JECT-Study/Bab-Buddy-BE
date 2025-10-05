package babbuddy.domain.voteroom.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VoteRoomDislikeResponseDto {
    private Long id;        // 싫어하는 음식 ID
    private String foodName; // 음식 이름
}

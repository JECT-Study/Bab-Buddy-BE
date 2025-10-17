package babbuddy.domain.voteroom.presentation.dto.response;

import babbuddy.domain.voteroom.domain.entity.MenuSelectMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteRoomMenuSelectMethodResponseDto {
    private MenuSelectMethod menuSelectMethod;
}

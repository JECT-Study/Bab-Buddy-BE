package babbuddy.domain.voteroom.presentation.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteRoomUser {
    private String id;
    private String name;
}

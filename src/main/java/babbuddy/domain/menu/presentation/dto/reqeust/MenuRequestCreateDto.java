package babbuddy.domain.menu.presentation.dto.reqeust;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuRequestCreateDto {
    private String name;
    private String voteRoomId;
}

package babbuddy.domain.vote.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuInfoDto {
    private String menuName;
    private int voteCount;
}

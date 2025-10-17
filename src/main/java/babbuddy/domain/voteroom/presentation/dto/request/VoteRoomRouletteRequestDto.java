package babbuddy.domain.voteroom.presentation.dto.request;

import babbuddy.domain.voteroom.domain.entity.MenuSelectMethod;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VoteRoomRouletteRequestDto {

    @NotNull(message = "메뉴 선택 방법은 필수입니다.")
    private MenuSelectMethod menuSelectMethod;

    @NotNull(message = "메뉴 이름은 필수입니다.")
    private String menuName;
}

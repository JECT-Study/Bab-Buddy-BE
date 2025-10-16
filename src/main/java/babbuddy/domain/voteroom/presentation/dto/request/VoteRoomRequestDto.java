package babbuddy.domain.voteroom.presentation.dto.request;


import babbuddy.domain.voteroom.domain.entity.MenuSelectMethod;
import babbuddy.domain.voteroom.domain.entity.VoteStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class VoteRoomRequestDto {

    @NotBlank(message = "투표방 제목은 필수입니다.")
    private String title;
    
    private VoteStatus status;
    
    @NotNull(message = "메뉴 선택 방법은 필수입니다.")
    private MenuSelectMethod menuSelectMethod;

}

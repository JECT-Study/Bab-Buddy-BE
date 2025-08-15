package babbuddy.domain.vote.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteResultDto {
    private List<RankedMenuGroupDto> topMenus;
}

package babbuddy.domain.vote.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RankedMenuGroupDto {
    private int rank;
    private int count;
    private List<MenuInfoDto> menus;
}

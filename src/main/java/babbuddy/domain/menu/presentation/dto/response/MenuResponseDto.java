package babbuddy.domain.menu.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuResponseDto {
    private String menuId;
    private String name;
    private String createdBy;
}

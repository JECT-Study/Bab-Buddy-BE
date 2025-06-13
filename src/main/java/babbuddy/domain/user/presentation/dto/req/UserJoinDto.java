package babbuddy.domain.user.presentation.dto.req;

import babbuddy.domain.user.domain.entity.Role;

import java.time.LocalDateTime;

public record UserJoinDto(

        String email,
        String username,
        String password,
        String name,
        String profile,
        String phoneNumber,
        LocalDateTime createdAt,
        Role role

) {
}

package babbuddy.domain.user.presentation.dto.res;

import babbuddy.domain.user.domain.entity.User;

public record GetUserRes(
        String name,

        String profile,

        String email
) {
    public static GetUserRes of(User user) {
        return new GetUserRes(user.getName(), user.getProfile(), user.getEmail());
    }
}

package babbuddy.domain.vote.presentation.dto.req;

public record CastVoteReq(
        String voteRoomId,
        String menuId
) {

}

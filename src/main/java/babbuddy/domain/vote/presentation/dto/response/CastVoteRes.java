package babbuddy.domain.vote.presentation.dto.response;

public record CastVoteRes(
        String voteId
) {
    public static CastVoteRes of(String voteId) {
        return new CastVoteRes(voteId);
    }
}

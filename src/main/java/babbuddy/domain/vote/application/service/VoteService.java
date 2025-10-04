package babbuddy.domain.vote.application.service;


import babbuddy.domain.vote.presentation.dto.response.CastVoteRes;

public interface VoteService {
    CastVoteRes castVote(String userId, String voteRoomId, String menuId);
    void cancelVote(String userId, String voteId);
}

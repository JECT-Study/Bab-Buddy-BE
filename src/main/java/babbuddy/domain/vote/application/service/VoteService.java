package babbuddy.domain.vote.application.service;



public interface VoteService {
    String castVote(String userId, String voteRoomId, String menuId);
    void cancelVote(String userId, String voteId);
}

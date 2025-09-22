package babbuddy.domain.voteroom.domain.entity;

/**
 * 투표방 상태
 */
public enum VoteStatus {
    ONGOING("진행 중"),
    FINISHED("종료");

    private final String label;

    VoteStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}

package babbuddy.domain.voteroom.domain.repository;

import babbuddy.domain.user.domain.entity.User;
import babbuddy.domain.voteroom.domain.entity.VoteRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRoomRepository extends JpaRepository<VoteRoom, String> {
}

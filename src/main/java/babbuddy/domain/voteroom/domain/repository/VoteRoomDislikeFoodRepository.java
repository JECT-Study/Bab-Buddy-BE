package babbuddy.domain.voteroom.domain.repository;

import babbuddy.domain.voteroom.domain.entity.VoteRoom;
import babbuddy.domain.voteroom.domain.entity.VoteRoomDislikeFood;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VoteRoomDislikeFoodRepository extends JpaRepository<VoteRoomDislikeFood, Long> {
    List<VoteRoomDislikeFood> findByVoteRoom(VoteRoom room);
    Optional<VoteRoomDislikeFood> findByVoteRoomAndFoodName(VoteRoom room, String foodName);
}

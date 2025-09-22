package babbuddy.domain.vote.domain.repository;

import babbuddy.domain.user.domain.entity.User;
import babbuddy.domain.vote.domain.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, String> {
    boolean existsByUserIdAndVoteRoomId(String userId, String voteRoomId);

    @Query("""
    SELECT COUNT(DISTINCT v.user.id)
    FROM Vote v
    WHERE v.voteRoom.Id = :roomId
    """)
    int countDistinctUsersByRoomId(@Param("roomId") String roomId);

    @Query("SELECT DISTINCT v.user FROM Vote v WHERE v.voteRoom.Id = :roomId")
    List<User> findParticipantsByRoomId(String roomId);

    @Query("SELECT v.menu.name, COUNT(v) FROM Vote v WHERE v.voteRoom.Id = :roomId GROUP BY v.menu.name")
    List<Object[]> countVotesGroupByMenu(@Param("roomId") String roomId);
}

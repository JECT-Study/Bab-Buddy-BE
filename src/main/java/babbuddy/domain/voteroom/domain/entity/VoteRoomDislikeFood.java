package babbuddy.domain.voteroom.domain.entity;

import babbuddy.domain.user.domain.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;


@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
public class VoteRoomDislikeFood {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // 다대일: 여러 VoteRoom이 한 User를 참조
    @JoinColumn(name = "vote_room_id")      // FK 컬럼 이름
    private VoteRoom voteRoom;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User createdBy;

    private String foodName;
}

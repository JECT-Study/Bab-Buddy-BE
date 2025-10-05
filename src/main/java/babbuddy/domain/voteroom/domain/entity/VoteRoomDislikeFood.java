package babbuddy.domain.voteroom.domain.entity;

import babbuddy.domain.menu.domain.entity.Menu;
import babbuddy.domain.user.domain.entity.User;
import babbuddy.domain.vote.domain.entity.Vote;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Entity
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



    private String foodName;


    @Builder
    public VoteRoomDislikeFood(String foodName, VoteRoom voteRoom) {
        this.foodName = foodName;
        this.voteRoom = voteRoom;
    }

}

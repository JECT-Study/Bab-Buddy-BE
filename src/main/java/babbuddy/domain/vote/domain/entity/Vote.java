package babbuddy.domain.vote.domain.entity;

import babbuddy.domain.menu.domain.entity.Menu;
import babbuddy.domain.user.domain.entity.User;
import babbuddy.domain.voteroom.domain.entity.VoteRoom;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@Table(name = "vote")
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "vote_id")
    private String Id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private VoteRoom voteRoom;

    @CreationTimestamp
    private LocalDateTime createdAt;


    @Builder
    public Vote(User user, Menu menu, VoteRoom voteRoom) {
        this.user = user;
        this.menu = menu;
        this.voteRoom = voteRoom;
    }

}

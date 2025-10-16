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
@Table(name = "voteroom")
public class VoteRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "room_id")
    private String Id;


    @ManyToOne(fetch = FetchType.LAZY) // 다대일: 여러 VoteRoom이 한 User를 참조
    @JoinColumn(name = "user_id")      // FK 컬럼 이름
    private User user;

    @OneToMany(mappedBy = "voteRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VoteRoomDislikeFood> voteRoomDislikeFoods = new ArrayList<>();

    @Column(name = "title", nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    private VoteStatus votestatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "menu_select_method", nullable = false)
    private MenuSelectMethod menuSelectMethod;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "voteRoom", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Menu> menus = new ArrayList<>();

    @Builder
    public VoteRoom(String title, VoteStatus votestatus, User user) {
        this.title = title;
        this.votestatus = votestatus;
        this.user = user;
    }

    public void changeStatus(VoteStatus newStatus) {
        this.votestatus = newStatus;
    }

}

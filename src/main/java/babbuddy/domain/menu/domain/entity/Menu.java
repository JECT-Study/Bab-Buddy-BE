package babbuddy.domain.menu.domain.entity;

import babbuddy.domain.user.domain.entity.User;
import babbuddy.domain.vote.domain.entity.Vote;
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
import java.util.ArrayList;
import java.util.List;
import org.springframework.lang.Nullable;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@Table(name = "menu")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "menu_id")
    private String Id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private VoteRoom voteRoom;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User createdBy;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Vote> votes = new ArrayList<>();
    
    // 이 메뉴를 룰렛으로 선택한 VoteRoom (양방향 매핑)
    @Nullable
    @OneToOne(mappedBy = "selectedMenu")
    private VoteRoom selectedByVoteRoom;

    @Builder
    public Menu(String name, VoteRoom voteRoom, User createdBy) {
        this.name = name;
        this.voteRoom = voteRoom;
        this.createdBy = createdBy;
    }

    public void changeName(String name) {
        this.name = name;
    }
    
    // 이 메뉴가 룰렛으로 선택되었는지 확인
    public boolean isSelectedByRoulette() {
        return selectedByVoteRoom != null;
    }
    
    // 이 메뉴를 룰렛으로 선택한 VoteRoom 조회
    public VoteRoom getSelectedByVoteRoom() {
        return selectedByVoteRoom;
    }

}

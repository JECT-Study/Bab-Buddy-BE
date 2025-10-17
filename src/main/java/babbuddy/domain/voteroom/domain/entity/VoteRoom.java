package babbuddy.domain.voteroom.domain.entity;

import babbuddy.domain.menu.domain.entity.Menu;
import babbuddy.domain.user.domain.entity.User;
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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@Table(name = "voteroom")
public class VoteRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "room_id")
    private String id;


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

    // 투표방 참여자 목록 (호스트 포함)
    @ManyToMany
    @JoinTable(
        name = "vote_room_participants",
        joinColumns = @JoinColumn(name = "room_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> participants = new ArrayList<>();

    public void changeStatus(VoteStatus newStatus) {
        this.votestatus = newStatus;
    }

    // 참여자 추가 메서드
    public void addParticipant(User participant) {
        if (!this.participants.contains(participant)) {
            this.participants.add(participant);
        }
    }

    // 참여자 제거 메서드
    public void removeParticipant(User participant) {
        this.participants.remove(participant);
    }

    // 전체 참여자 수 조회 (호스트 포함)
    public int getTotalParticipantCount() {
        return participants.size();
    }
}

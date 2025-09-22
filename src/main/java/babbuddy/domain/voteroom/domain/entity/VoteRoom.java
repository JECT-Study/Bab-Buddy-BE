package babbuddy.domain.voteroom.domain.entity;

import babbuddy.domain.menu.domain.entity.Menu;
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

    @Column(name = "title", nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    private VoteStatus votestatus;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "voteRoom", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Menu> menus = new ArrayList<>();

    @Builder
    public VoteRoom(String title, VoteStatus votestatus) {
        this.title = title;
        this.votestatus = votestatus;
    }

    public void changeStatus(VoteStatus newStatus) {
        this.votestatus = newStatus;
    }

}

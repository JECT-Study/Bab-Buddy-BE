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

    @Builder
    public Menu(String name, VoteRoom voteRoom, User createdBy) {
        this.name = name;
        this.voteRoom = voteRoom;
        this.createdBy = createdBy;
    }

    public void changeName(String name) {
        this.name = name;
    }

}

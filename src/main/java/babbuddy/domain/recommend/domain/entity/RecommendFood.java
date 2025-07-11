package babbuddy.domain.recommend.domain.entity;

import babbuddy.domain.user.domain.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@DynamicUpdate
public class RecommendFood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String foodName;

    @Column(nullable = false)
    private String foodIntroduce;

    @Column(nullable = false)
    private boolean isFavorite;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Builder
    public RecommendFood(User user, String foodName, String foodIntroduce, boolean isFavorite) {
        this.user = user;
        this.foodName = foodName;
        this.foodIntroduce = foodIntroduce;
        this.isFavorite = isFavorite;
    }
}

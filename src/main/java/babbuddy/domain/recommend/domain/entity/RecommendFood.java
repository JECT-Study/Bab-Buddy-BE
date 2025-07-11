package babbuddy.domain.recommend.domain.entity;

import babbuddy.domain.dislikefood.domain.entity.DislikeFood;
import babbuddy.domain.user.domain.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "recommendFood", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecommendRestaurant> recommendRestaurants = new ArrayList<>();

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

package babbuddy.domain.food.domain.entity;

import babbuddy.domain.user.domain.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@Entity
@NoArgsConstructor
@DynamicUpdate
public class DislikeFood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String foodName;

    @Builder
    public DislikeFood(User user, String foodName) {
        this.user = user;
        this.foodName = foodName;
    }
}

package babbuddy.domain.recommend.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
public class RecommendRestaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recommend_food_id", nullable = false)
    private RecommendFood recommendFood;

    @Column(nullable = false)
    private String restaurantName;

    // 음식점 종류 (한식, 양식, 일식 등) - Enum 혹은 String 귀찮으니 String
    @Column(nullable = false)
    private String restaurantType;

    // 주소
    private String address;

    // 음식점 평점
    private String rate;
    // 위도
    private Double latitude;

    // 경도
    private Double longitude;

    @Column(nullable = false)
    private boolean isFavorite;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Builder
    public RecommendRestaurant(RecommendFood recommendFood,
                               String restaurantName,
                               String restaurantType,
                               String address,
                               String rate,
                               Double latitude,
                               Double longitude,
                               boolean isFavorite,
                               LocalDateTime createdAt) {
        this.recommendFood = recommendFood;
        this.restaurantName = restaurantName;
        this.restaurantType = restaurantType;
        this.address = address;
        this.rate = rate;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isFavorite = isFavorite;
        this.createdAt=createdAt;
    }


    public void updateIsFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }
}

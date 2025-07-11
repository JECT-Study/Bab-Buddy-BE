package babbuddy.domain.recommend.domain.repository;


import babbuddy.domain.recommend.domain.entity.RecommendFood;
import babbuddy.domain.recommend.domain.entity.RecommendRestaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecommendRestaurantRepository extends JpaRepository<RecommendRestaurant, Long> {
    List<RecommendRestaurant> findByRecommendFood(RecommendFood recommendFood);

}

package babbuddy.domain.recommend.domain.repository;


import babbuddy.domain.recommend.domain.entity.RecommendFood;
import babbuddy.domain.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecommendFoodRepository extends JpaRepository<RecommendFood, Long> {

}

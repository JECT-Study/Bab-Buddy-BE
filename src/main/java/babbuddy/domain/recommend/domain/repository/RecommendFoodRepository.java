package babbuddy.domain.recommend.domain.repository;


import babbuddy.domain.recommend.domain.entity.RecommendFood;
import babbuddy.domain.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RecommendFoodRepository extends JpaRepository<RecommendFood, Long> {
    List<RecommendFood> findAllByUser(User user);
}

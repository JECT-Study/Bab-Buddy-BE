package babbuddy.domain.food.domain.repository;

import babbuddy.domain.food.domain.entity.Food;
import babbuddy.domain.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findAllByUser(User user);
}

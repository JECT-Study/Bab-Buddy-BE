package babbuddy.domain.dislikefood.domain.repository;

import babbuddy.domain.dislikefood.domain.entity.DislikeFood;
import babbuddy.domain.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DisLikeFoodRepository extends JpaRepository<DislikeFood, Long> {
    List<DislikeFood> findAllByUser(User user);
}

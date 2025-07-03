package babbuddy.domain.allergy.domain.repository;

import babbuddy.domain.allergy.domain.entity.Allergy;
import babbuddy.domain.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AllergyRepository extends JpaRepository<Allergy, Long> {
    void deleteByUser(User user);
    List<Allergy> findByUser(User user);
}

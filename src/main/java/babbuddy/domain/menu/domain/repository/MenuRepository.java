package babbuddy.domain.menu.domain.repository;

import babbuddy.domain.menu.domain.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, String> {
    List<Menu> findAllById(String voteRoomId);
}

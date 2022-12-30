package kw.ic.backend.domain.menu.repository;

import java.util.List;
import kw.ic.backend.domain.menu.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu,Long>,MenuRepositoryDSL {

    public List<Menu> findAllByRestaurantId(Long restaurantId);
}

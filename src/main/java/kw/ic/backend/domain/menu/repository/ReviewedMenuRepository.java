package kw.ic.backend.domain.menu.repository;

import kw.ic.backend.domain.menu.ReviewedMenu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewedMenuRepository extends JpaRepository<ReviewedMenu, Long> {
}

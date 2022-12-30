package kw.ic.backend.domain.menu.repository;

import kw.ic.backend.domain.menu.Menu;
import kw.ic.backend.domain.menu.dto.request.MenuPageRequest;
import org.springframework.data.domain.Page;

public interface MenuRepositoryDSL {

    Page<Menu> findMenusWithPagination(MenuPageRequest request);
}

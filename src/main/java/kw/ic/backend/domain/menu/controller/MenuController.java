package kw.ic.backend.domain.menu.controller;

import java.util.List;
import kw.ic.backend.domain.menu.dto.SimpleMenu;
import kw.ic.backend.domain.menu.dto.request.MenuPageRequest;
import kw.ic.backend.domain.menu.dto.request.MenuRequest;
import kw.ic.backend.domain.menu.dto.response.MenuPageResponse;
import kw.ic.backend.domain.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/menus")
public class MenuController {

    private final MenuService menuService;

    @GetMapping("")
    ResponseEntity<MenuPageResponse> findMenusByRestaurant(MenuPageRequest request) {
        log.info("find menus of restaurantId: {} with pagination", request);

        MenuPageResponse response = menuService.findMenus(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    ResponseEntity<List<SimpleMenu>> findMenusByRestaurantId(Long restaurantId) {
        List<SimpleMenu> response = menuService.findAllMenusByRestaurantId(restaurantId);

        return ResponseEntity.ok(response);
    }

    @PostMapping("")
    ResponseEntity<Long> register(@RequestBody MenuRequest request) {
        log.info("Register menu of restaurant id: {}", request.getRestaurantId());

        Long menuId = menuService.register(request);

        return ResponseEntity.ok(menuId);
    }

    @DeleteMapping("/{menu_id}")
    ResponseEntity<Long> delete(@PathVariable(name = "menu_id") Long menuId) {
        log.info("delete menu");

        Long deletedId = menuService.delete(menuId);

        return ResponseEntity.ok(deletedId);
    }
}

package kw.ic.backend.domain.menu.dto;

import kw.ic.backend.domain.menu.Menu;
import kw.ic.backend.domain.menu.dto.response.MenuResponse;
import org.springframework.stereotype.Component;

@Component
public class MenuResponseAssembler {

    public MenuResponse menuResponse(Menu menu) {
        return MenuResponse.builder()
                .id(menu.getId())
                .menuName(menu.getMenuName())
                .price(menu.getPrice())
                .description(menu.getDescription())
                .restaurant_id(menu.getRestaurant().getId())
                .build();
    }

}

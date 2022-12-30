package kw.ic.backend.domain.menu.dto.request;

import javax.validation.constraints.NotNull;
import kw.ic.backend.domain.menu.Menu;
import kw.ic.backend.domain.restaurant.entity.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MenuRequest {

    @NotNull
    private String menuName;

    @NotNull
    private Integer price;

    private String description;

    @NotNull
    private Long restaurantId;

    public Menu toMenu() {
        return Menu.builder()
                .menuName(this.menuName)
                .price(this.price)
                .description(this.description)
                .restaurant(new Restaurant(this.restaurantId))
                .build();
    }
}

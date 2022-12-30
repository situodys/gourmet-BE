package kw.ic.backend.domain.menu.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MenuResponse {

    private Long id;

    private String menuName;

    private Integer price;

    private String description;

    private Long restaurant_id;

    @Builder
    public MenuResponse(Long id, String menuName, Integer price, String description, Long restaurant_id) {
        this.id = id;
        this.menuName = menuName;
        this.price = price;
        this.description = description;
        this.restaurant_id = restaurant_id;
    }
}

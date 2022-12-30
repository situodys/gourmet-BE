package kw.ic.backend.domain.menu.dto.response;

import java.util.List;
import kw.ic.backend.global.dto.BasePageResponse;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class MenuPageResponse extends BasePageResponse {

    private List<MenuResponse> menus;

    public MenuPageResponse(Page<MenuResponse> result) {
        super(result);
        this. menus = result.getContent();
    }
}

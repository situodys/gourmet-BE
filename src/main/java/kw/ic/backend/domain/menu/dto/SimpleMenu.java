package kw.ic.backend.domain.menu.dto;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SimpleMenu {

    @NotNull
    private Long menuId;

    @NotNull
    private String menuName;
}

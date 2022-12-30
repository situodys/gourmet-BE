package kw.ic.backend.global.auth.dto;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuthRequest {

    @NotNull
    private String email;

    @NotNull
    private String password;
}

package kw.ic.backend.domain.member.dto;

import kw.ic.backend.domain.member.dto.embbed.Authority;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SimpleMember {

    private Long memberId;
    private String email;
    private Authority authority;
}

package kw.ic.backend.domain.proposal.dto.response;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import kw.ic.backend.domain.member.Member;
import kw.ic.backend.domain.proposal.dto.embbed.Category;
import kw.ic.backend.domain.restaurant.entity.Restaurant;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@NoArgsConstructor
public class ProposalResponse {

    private Long proposalId;

    private String title;

    private Category category;

    private String content;

    private String status;

    private Long restaurantId;

    private Long memberId;

    private Long menuId;

    @Builder
    public ProposalResponse(Long proposalId, String title, Category category, String content, String status,
                            Long restaurantId, Long memberId, Long menuId) {
        this.proposalId = proposalId;
        this.title = title;
        this.category = category;
        this.content = content;
        this.status = status;
        this.restaurantId = restaurantId;
        this.memberId = memberId;
        this.menuId = menuId;
    }
}

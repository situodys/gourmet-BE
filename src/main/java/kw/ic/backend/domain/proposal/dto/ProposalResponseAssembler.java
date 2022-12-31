package kw.ic.backend.domain.proposal.dto;

import kw.ic.backend.domain.menu.Menu;
import kw.ic.backend.domain.proposal.Proposal;
import kw.ic.backend.domain.proposal.dto.response.ProposalPageResponse;
import kw.ic.backend.domain.proposal.dto.response.ProposalResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class ProposalResponseAssembler {

    public ProposalResponse createProposalResponse(Proposal proposal) {
        Menu menu = proposal.getMenu();
        Long menuId = null;
        if (menu != null) {
            menuId = menu.getId();
        }
        return ProposalResponse.builder()
                .proposalId(proposal.getId())
                .title(proposal.getTitle())
                .category(proposal.getCategory())
                .content(proposal.getContent())
                .status(proposal.getStatus())
                .memberId(proposal.getMember().getId())
                .restaurantId(proposal.getRestaurant().getId())
                .menuId(menuId)
                .build();
    }

    public ProposalPageResponse createProposalPageResponse(Page<ProposalResponse> result) {
        return new ProposalPageResponse(result);
    }
}

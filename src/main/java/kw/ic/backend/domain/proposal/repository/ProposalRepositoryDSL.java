package kw.ic.backend.domain.proposal.repository;

import kw.ic.backend.domain.proposal.Proposal;
import kw.ic.backend.domain.proposal.dto.request.ProposalPageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProposalRepositoryDSL {

    Page<Proposal> findProposals(ProposalPageRequest request);
    Page<Proposal> findProposalsByRestaurantId(Long restaurantId, ProposalPageRequest request);

}

package kw.ic.backend.domain.proposal.service;

import kw.ic.backend.domain.member.repository.MemberRepository;
import kw.ic.backend.domain.menu.Menu;
import kw.ic.backend.domain.menu.repository.MenuRepository;
import kw.ic.backend.domain.proposal.Proposal;
import kw.ic.backend.domain.proposal.dto.ProposalResponseAssembler;
import kw.ic.backend.domain.proposal.dto.request.ProposalPageRequest;
import kw.ic.backend.domain.proposal.dto.request.ProposalRequest;
import kw.ic.backend.domain.proposal.dto.response.ProposalPageResponse;
import kw.ic.backend.domain.proposal.dto.response.ProposalResponse;
import kw.ic.backend.domain.proposal.repository.ProposalRepository;
import kw.ic.backend.domain.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ProposalService {

    private final ProposalRepository proposalRepository;
    private final MemberRepository memberRepository;
    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;

    private final ProposalResponseAssembler responseAssembler;

    public ProposalPageResponse findProposals(ProposalPageRequest request) {
        Page<ProposalResponse> result = proposalRepository.findProposals(request)
                .map(proposal -> responseAssembler.createProposalResponse(proposal));

        return responseAssembler.createProposalPageResponse(result);
    }

    public ProposalPageResponse findProposalsByRestaurantId(Long restaurantId, ProposalPageRequest request) {
        Page<ProposalResponse> result = proposalRepository.findProposalsByRestaurantId(restaurantId, request)
                .map(proposal -> responseAssembler.createProposalResponse(proposal));

        return responseAssembler.createProposalPageResponse(result);
    }

    public Long register(ProposalRequest request) {
        Menu menu = null;
        if (request.getMenuId() != null) {
            menu = menuRepository.getReferenceById(request.getMenuId());
        }

        Proposal proposal = proposalRepository.save(
                request.toProposal(
                        restaurantRepository.getReferenceById(request.getRestaurantId()),
                        memberRepository.getReferenceById(request.getMemberId()),
                        menu
                )
        );

        return proposal.getId();
    }

    public Long delete(Long proposalId) {
        proposalRepository.deleteById(proposalId);

        return proposalId;
    }
}

package kw.ic.backend.domain.proposal.service;

import java.util.stream.Collectors;
import kw.ic.backend.domain.member.repository.MemberRepository;
import kw.ic.backend.domain.menu.Menu;
import kw.ic.backend.domain.menu.repository.MenuRepository;
import kw.ic.backend.domain.proposal.Proposal;
import kw.ic.backend.domain.proposal.dto.request.ProposalPageRequest;
import kw.ic.backend.domain.proposal.dto.request.ProposalRequest;
import kw.ic.backend.domain.proposal.dto.response.ProposalPageResponse;
import kw.ic.backend.domain.proposal.dto.response.ProposalResponse;
import kw.ic.backend.domain.proposal.repository.ProposalRepository;
import kw.ic.backend.domain.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public ProposalPageResponse findProposals(ProposalPageRequest request) {

        Page<ProposalResponse> result = proposalRepository.findProposals(request)
                .map(proposal -> {
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
                );

        return new ProposalPageResponse(result);
    }

    public Long register(ProposalRequest request) {
        if (request.getMenuId() == null) {
            Proposal proposal = proposalRepository.save(request.toProposal(
                    restaurantRepository.getReferenceById(request.getRestaurantId()),
                    memberRepository.getReferenceById(request.getMemberId()),
                    null));
            return proposal.getId();
        }
        Proposal proposal = proposalRepository.save(request.toProposal(
                restaurantRepository.getReferenceById(request.getRestaurantId()),
                memberRepository.getReferenceById(request.getMemberId()),
                menuRepository.getReferenceById(request.getMenuId())));
        return proposal.getId();
    }

    public Long delete(Long proposalId) {

        proposalRepository.deleteById(proposalId);

        return proposalId;
    }

    public ProposalPageResponse findProposalsByRestaurantId(Long restaurantId, ProposalPageRequest request) {
        Page<ProposalResponse> result = proposalRepository.findProposalsByRestaurantId(restaurantId, request)
                .map(proposal -> {
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
                );

        return new ProposalPageResponse(result);
    }
}

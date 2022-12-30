package kw.ic.backend.domain.proposal.controller;

import kw.ic.backend.domain.proposal.dto.request.ProposalPageRequest;
import kw.ic.backend.domain.proposal.dto.request.ProposalRequest;
import kw.ic.backend.domain.proposal.dto.response.ProposalPageResponse;
import kw.ic.backend.domain.proposal.service.ProposalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/proposals")
public class ProposalController {

    private final ProposalService proposalService;

    @GetMapping("")
    public ResponseEntity<ProposalPageResponse> findProposals(ProposalPageRequest request) {
        log.info(" findProposals: {}", request);

        ProposalPageResponse response = proposalService.findProposals(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/restaurant/{restaurant_id}")
    public ResponseEntity<ProposalPageResponse> findProposalsByRestaurantId(
            @PathVariable(name = "restaurant_id") Long restaurantId, ProposalPageRequest request) {

        ProposalPageResponse response = proposalService.findProposalsByRestaurantId(restaurantId, request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("")
    public ResponseEntity<Long> register(@RequestBody ProposalRequest proposalRequest) {
        log.info("register proposal");

        Long proposalId = proposalService.register(proposalRequest);

        return ResponseEntity.ok(proposalId);
    }

    @DeleteMapping("/{proposal_id}")
    public ResponseEntity<Long> delete(@PathVariable(name="proposal_id") Long proposalId) {
        log.info("delete proposal of Id: {}", proposalId);

        Long deleteId = proposalService.delete(proposalId);

        return ResponseEntity.ok(deleteId);
    }
}

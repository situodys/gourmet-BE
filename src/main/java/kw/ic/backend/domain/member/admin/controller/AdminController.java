package kw.ic.backend.domain.member.admin.controller;

import kw.ic.backend.domain.member.admin.service.AdminService;
import kw.ic.backend.domain.proposal.dto.request.ProposalRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/proposal/apply")
    public ResponseEntity<Void> applyProposal(@RequestBody ProposalRequest request) {

        adminService.apply(request);

        return ResponseEntity.ok(null);
    }

    @PostMapping("/proposal/refuse")
    public ResponseEntity<Void> refuseProposal(@RequestBody ProposalRequest request) {

        adminService.refuse(request);

        return ResponseEntity.ok(null);
    }
}

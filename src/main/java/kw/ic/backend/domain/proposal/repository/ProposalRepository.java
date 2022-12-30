package kw.ic.backend.domain.proposal.repository;

import java.util.List;
import kw.ic.backend.domain.proposal.Proposal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProposalRepository extends JpaRepository<Proposal,Long>,ProposalRepositoryDSL {
}

package kw.ic.backend.domain.proposal.dto.response;

import java.util.List;
import kw.ic.backend.domain.proposal.Proposal;
import kw.ic.backend.global.dto.BasePageResponse;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class ProposalPageResponse extends BasePageResponse {

    private List<ProposalResponse> data;

    public ProposalPageResponse(Page<ProposalResponse> result) {
        super(result);
        this.data = result.getContent();
    }
}

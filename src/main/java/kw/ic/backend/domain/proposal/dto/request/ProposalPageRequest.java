package kw.ic.backend.domain.proposal.dto.request;

import javax.validation.constraints.Min;
import kw.ic.backend.global.dto.BasePageRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Setter
@Getter
public class ProposalPageRequest extends BasePageRequest {

    public ProposalPageRequest(@Min(value = 0L) Integer page,
                               @Min(value = 1L) Integer size,
                               Sort sort, Long totalCount) {
        super(page, size, sort, totalCount);
    }
}

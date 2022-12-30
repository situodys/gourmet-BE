package kw.ic.backend.domain.review.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ReviewPageResponse {

    List<SimpleReviewResponse> data;

    private Long lastId;

}

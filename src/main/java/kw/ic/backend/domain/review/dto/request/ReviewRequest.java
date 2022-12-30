package kw.ic.backend.domain.review.dto.request;

import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import kw.ic.backend.domain.member.Member;
import kw.ic.backend.domain.menu.dto.SimpleMenu;
import kw.ic.backend.domain.restaurant.entity.Restaurant;
import kw.ic.backend.domain.review.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequest {

    @NotNull
    private String title;

    @NotNull
    private String content;

    @NotNull
    private Integer rating;

    @NotNull
    private Long memberId;

    @NotNull
    private Long restaurantId;

    @NotNull @NotEmpty
    private List<SimpleMenu> simpleMenus;

    public Review toReview() {
        return Review.builder()
                .title(this.title)
                .content(this.content)
                .rating(this.rating)
                .member(new Member(this.memberId))
                .restaurant(new Restaurant(this.restaurantId))
                .build();
    }
}

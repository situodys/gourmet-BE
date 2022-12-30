package kw.ic.backend.domain.review.dto;

import java.util.List;
import java.util.stream.Collectors;
import kw.ic.backend.domain.menu.ReviewedMenu;
import kw.ic.backend.domain.restaurant.dto.RestaurantResponseAssembler;
import kw.ic.backend.domain.review.dto.response.ReviewPageResponse;
import kw.ic.backend.domain.review.dto.response.ReviewResponse;
import kw.ic.backend.domain.review.dto.response.SimpleReviewResponse;
import kw.ic.backend.domain.review.entity.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewResponseAssembler {

    private static final int GRADE_START_INDEX=0;
    private static final int GRADE_END_INDEX=5;

    private final RestaurantResponseAssembler restaurantResponseAssembler;

    public ReviewResponse reviewResponse(final Review review) {
        return new ReviewResponse(
                review.getId(),
                review.getTitle(),
                review.getContent(),
                review.getRating(),
                review.getMember().getEmail().substring(GRADE_START_INDEX, GRADE_END_INDEX),
                restaurantResponseAssembler.simpleRestaurantResponse(review.getRestaurant()),
                review.getReviewedMenus().stream()
                        .map(ReviewedMenu::getMenuName)
                        .collect(Collectors.toUnmodifiableList())
        );
    }

    public SimpleReviewResponse simpleReviewResponse(final Review review) {
        return SimpleReviewResponse.builder()
                .reviewId(review.getId())
                .title(review.getTitle())
                .content(review.getContent())
                .rating(review.getRating())
                .emailPrefix(review.getMember().getEmail().substring(GRADE_START_INDEX, GRADE_END_INDEX))
                .createdAt(review.getCreatedAt())
                .menus(review.getReviewedMenus()
                        .stream()
                        .map(ReviewedMenu::getMenuName)
                        .collect(Collectors.toUnmodifiableList()))
                .build();
    }

    public ReviewPageResponse reviewPageResponse(List<SimpleReviewResponse> simpleReviewResponses) {
        if (simpleReviewResponses.size() == 0) {
            return new ReviewPageResponse(simpleReviewResponses, 0L);
        }
        return new ReviewPageResponse((simpleReviewResponses),
                simpleReviewResponses.get(simpleReviewResponses.size() - 1).getReviewId());
    }
}

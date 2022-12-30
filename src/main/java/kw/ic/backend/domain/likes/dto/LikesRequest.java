package kw.ic.backend.domain.likes.dto;

import javax.validation.constraints.NotNull;
import kw.ic.backend.domain.likes.Likes;
import kw.ic.backend.domain.member.Member;
import kw.ic.backend.domain.restaurant.entity.Restaurant;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LikesRequest {

    @NotNull
    private Long restaurantId;

    @NotNull
    private Long memberId;

    public LikesRequest(Long restaurantId, Long memberId) {
        this.restaurantId = restaurantId;
        this.memberId = memberId;
    }

    public Likes toLikes() {
        return Likes.builder()
                .member(new Member(this.memberId))
                .restaurant(new Restaurant(this.restaurantId))
                .build();
    }

}

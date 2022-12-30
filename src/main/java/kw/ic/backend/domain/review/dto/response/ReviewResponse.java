package kw.ic.backend.domain.review.dto.response;

import java.util.List;
import javax.validation.constraints.NotNull;
import kw.ic.backend.domain.restaurant.dto.response.SimpleRestaurantResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponse {

    @NotNull
    private Long reviewId;

    @NotNull
    private String title;

    @NotNull
    private String content;

    @NotNull
    private Integer rating;

    @NotNull
    private String grade;

    @NotNull
    private SimpleRestaurantResponse simpleRestaurantResponse;

    @NotNull
    private List<String> menus;
}

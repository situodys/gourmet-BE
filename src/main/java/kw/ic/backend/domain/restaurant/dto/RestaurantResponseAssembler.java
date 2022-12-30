package kw.ic.backend.domain.restaurant.dto;

import java.util.List;
import java.util.stream.Collectors;
import kw.ic.backend.domain.menu.Menu;
import kw.ic.backend.domain.menu.dto.MenuResponseAssembler;
import kw.ic.backend.domain.menu.dto.response.MenuResponse;
import kw.ic.backend.domain.notification.Notification;
import kw.ic.backend.domain.restaurant.dto.projection.RestaurantLikes;
import kw.ic.backend.domain.restaurant.dto.projection.RestaurantReviewRating;
import kw.ic.backend.domain.restaurant.dto.projection.RestaurantStatic;
import kw.ic.backend.domain.notification.dto.response.NotificationResponse;
import kw.ic.backend.domain.restaurant.dto.response.RestaurantLikesResponse;
import kw.ic.backend.domain.restaurant.dto.response.RestaurantPageResponse;
import kw.ic.backend.domain.restaurant.dto.response.RestaurantResponse;
import kw.ic.backend.domain.restaurant.dto.response.RestaurantReviewRatingResponse;
import kw.ic.backend.domain.restaurant.dto.response.RestaurantStaticResponse;
import kw.ic.backend.domain.restaurant.dto.response.SimpleRestaurantResponse;
import kw.ic.backend.domain.restaurant.entity.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class RestaurantResponseAssembler {

    private final MenuResponseAssembler menuResponseAssembler;

    public RestaurantResponse restaurantResponse(RestaurantStatic restaurantStatic,Boolean isLike) {
        return RestaurantResponse.builder().
                restaurantId(restaurantStatic.getRestaurant().getId())
                .name(restaurantStatic.getRestaurant().getName())
                .description(restaurantStatic.getRestaurant().getDescription())
                .type(restaurantStatic.getRestaurant().getType())
                .address(restaurantStatic.getRestaurant().getAddress())
                .runningTime(restaurantStatic.getRestaurant().getRunningTime())
                .likeCount(restaurantStatic.getLikeCount())
                .rating(restaurantStatic.getRating())
                .reviewCount(restaurantStatic.getReviewCount())
                .isLike(isLike)
                .build();
    }

    public SimpleRestaurantResponse simpleRestaurantResponse(Restaurant restaurant) {
        return SimpleRestaurantResponse.builder()
                .restaurantId(restaurant.getId())
                .name(restaurant.getName())
                .address(restaurant.getAddress())
                .runningTime(restaurant.getRunningTime())
                .restaurantType(restaurant.getType())
                .build();
    }

    public RestaurantStaticResponse restaurantStaticResponse(RestaurantStatic restaurantStatic) {
        return RestaurantStaticResponse.builder()
                .simpleRestaurantResponse(simpleRestaurantResponse(restaurantStatic.getRestaurant()))
                .likeCount(restaurantStatic.getLikeCount())
                .rating(restaurantStatic.getRating())
                .reviewCount(restaurantStatic.getReviewCount())
                .build();
    }

    public RestaurantLikesResponse restaurantLikesResponse(RestaurantLikes restaurantLikes) {
        return RestaurantLikesResponse.builder()
                .simpleRestaurantResponse(simpleRestaurantResponse(restaurantLikes.getRestaurant()))
                .likeCount(restaurantLikes.getLikeCount())
                .build();
    }

    public RestaurantReviewRatingResponse restaurantReviewRatingResponse(
            RestaurantReviewRating restaurantReviewRating) {
        return RestaurantReviewRatingResponse.builder()
                .simpleRestaurantResponse(simpleRestaurantResponse(restaurantReviewRating.getRestaurant()))
                .rating(restaurantReviewRating.getRating())
                .reviewCount(restaurantReviewRating.getReviewCount())
                .build();
    }

    public RestaurantPageResponse restaurantPageResponse(List<RestaurantStaticResponse> responses) {
        if (responses.size() == 0) {
            return new RestaurantPageResponse(responses,0L);
        }
        return new RestaurantPageResponse(responses,
                responses.get(responses.size() - 1).getSimpleRestaurantResponse().getRestaurantId());
    }

    private List<MenuResponse> menuResponses(List<Menu> menus) {
        if (isNull(menus)) {
            return List.of();
        }
        return menus.stream()
                .map(menu -> menuResponseAssembler.menuResponse(menu))
                .collect(Collectors.toList());
    }

    private List<NotificationResponse> notificationResponses(List<Notification> notifications) {
        if (isNull(notifications)) {
            return List.of();
        }
        return notifications.stream()
                .map(Notification::toResponse)
                .collect(Collectors.toList());
    }

    private boolean isNull(List<?> lists) {
        return lists == null;
    }
}

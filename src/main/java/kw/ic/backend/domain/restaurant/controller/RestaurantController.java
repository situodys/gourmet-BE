package kw.ic.backend.domain.restaurant.controller;

import java.security.Principal;
import java.util.List;
import kw.ic.backend.domain.restaurant.dto.request.RestaurantPageRequest;
import kw.ic.backend.domain.restaurant.dto.request.RestaurantRequest;
import kw.ic.backend.domain.restaurant.dto.response.RestaurantLikesResponse;
import kw.ic.backend.domain.restaurant.dto.response.RestaurantPageResponse;
import kw.ic.backend.domain.restaurant.dto.response.RestaurantResponse;
import kw.ic.backend.domain.restaurant.dto.response.RestaurantReviewRatingResponse;
import kw.ic.backend.domain.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping("")
    public ResponseEntity<RestaurantPageResponse> findRestaurants(
            RestaurantPageRequest request) {

        log.info("find restaurants: {}", request.getRestaurantType());
        RestaurantPageResponse response = restaurantService.findRestaurants(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/likes/top")
    public ResponseEntity<List<RestaurantLikesResponse>> findRestaurantsByMostLikes() {
        List<RestaurantLikesResponse> response = restaurantService.findRestaurantsByMostLikesLimit5();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/reviews/rating/top")
    public ResponseEntity<List<RestaurantReviewRatingResponse>> findRestaurantsByMostReviewRating() {
        List<RestaurantReviewRatingResponse> response = restaurantService.findRestaurantsByMostMostReviewRatingLimit5();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{restaurant_id}")
    public ResponseEntity<RestaurantResponse> findRestaurant(@PathVariable(name = "restaurant_id") Long restaurantId,
                                                             Principal principal) {
        log.info("find restaurant");
        Long memberId = null;
        if(principal != null){
            memberId= Long.parseLong(principal.getName());
        }

        RestaurantResponse response = restaurantService.findRestaurantByIdAndCheckIsLike(restaurantId,memberId);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/")
    public ResponseEntity<Long> register(@RequestBody RestaurantRequest request) {
        log.info("Register restaurant");

        Long restaurant_id = restaurantService.register(request);

        return ResponseEntity.ok(restaurant_id);
    }

    @DeleteMapping("/{restaurant_id}")
    public ResponseEntity<Long> delete(@PathVariable Long restaurant_id) {
        log.info("delete restaurant");

        restaurantService.delete(restaurant_id);

        return ResponseEntity.ok(restaurant_id);
    }
}

package kw.ic.backend.domain.restaurant.dto.response;

import javax.validation.constraints.NotNull;
import kw.ic.backend.domain.restaurant.dto.embbed.Address;
import kw.ic.backend.domain.restaurant.dto.embbed.RestaurantType;
import kw.ic.backend.domain.restaurant.dto.embbed.RunningTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SimpleRestaurantResponse {

    @NotNull
    Long restaurantId;

    @NotNull
    String name;

    @NotNull
    Address address;

    @NotNull
    RunningTime runningTime;

    RestaurantType restaurantType;

    @Builder
    public SimpleRestaurantResponse(Long restaurantId, String name, Address address, RunningTime runningTime, RestaurantType restaurantType) {
        this.restaurantId = restaurantId;
        this.name =name;
        this.address =address;
        this.runningTime = runningTime;
        this.restaurantType = restaurantType;
    }
}

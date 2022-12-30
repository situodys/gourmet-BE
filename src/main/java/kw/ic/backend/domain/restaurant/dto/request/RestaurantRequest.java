package kw.ic.backend.domain.restaurant.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import kw.ic.backend.domain.restaurant.entity.Restaurant;
import kw.ic.backend.domain.restaurant.dto.embbed.Address;
import kw.ic.backend.domain.restaurant.dto.embbed.RestaurantType;
import kw.ic.backend.domain.restaurant.dto.embbed.RunningTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantRequest {

    @NotBlank
    private String name;

    private String description;

    @NotNull
    private RestaurantType type;

    @NotNull
    private Address address;

    @NotNull
    private RunningTime runningTime;

    public Restaurant toRestaurant() {
        return Restaurant.builder()
                .name(name)
                .description(description)
                .type(type)
                .address(address)
                .runningTime(runningTime)
                .build();
    }
}

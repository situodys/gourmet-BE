package kw.ic.backend.restaurant.repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.IntStream;
import kw.ic.backend.domain.restaurant.dto.embbed.Address;
import kw.ic.backend.domain.restaurant.dto.embbed.RestaurantType;
import kw.ic.backend.domain.restaurant.dto.embbed.RunningTime;
import kw.ic.backend.domain.restaurant.dto.request.RestaurantRequest;
import kw.ic.backend.domain.restaurant.entity.Restaurant;
import kw.ic.backend.domain.restaurant.repository.RestaurantRepository;
import kw.ic.backend.global.config.JpaConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@Import(JpaConfig.class)
@TestInstance(Lifecycle.PER_CLASS)
public class RestaurantRepositoryTest {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @BeforeAll
    private void init() {
        IntStream.rangeClosed(1, 5)
                .forEach(idx -> {
                    Restaurant restaurant = Restaurant.builder()
                            .name("name"+idx)
                            .description("description"+idx)
                            .type(RestaurantType.KOREAN)
                            .address(new Address("city1", "street1", "zipcode"))
                            .runningTime(new RunningTime(LocalDateTime.now(), LocalDateTime.now()))
                            .build();

                    restaurantRepository.save(restaurant);
                });
    }

    @Test
    @DisplayName("식당 정상 등록 확인")
    public void register() throws Exception {
        //given
        RestaurantRequest request = new RestaurantRequest(
                "name",
                "description",
                RestaurantType.KOREAN,
                new Address("city1", "street1", "zipcode"),
                new RunningTime(LocalDateTime.now(), LocalDateTime.now())
        );

        //when
        Restaurant restaurant = restaurantRepository.save(request.toRestaurant());

        //then
        Assertions.assertThat(restaurant.getId()).isEqualTo(6L);

    }

    @Test
    @DisplayName("restaurant_id로 restaurant 찾기")
    public void findById() throws Exception {
        //given
        Long id = 1L;

        //when
        Optional<Restaurant> restaurant = restaurantRepository.findById(id);

        //then
        Assertions.assertThat(restaurant.isPresent()).isEqualTo(true);
    }
}

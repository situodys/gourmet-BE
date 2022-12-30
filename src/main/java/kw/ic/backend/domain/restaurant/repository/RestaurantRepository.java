package kw.ic.backend.domain.restaurant.repository;

import java.util.Optional;
import kw.ic.backend.domain.restaurant.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant,Long>,RestaurantRepositoryDSL {


    Restaurant save(Restaurant restaurant);

    Optional<Restaurant> findById(Long id);

    void deleteById(Long id);
}

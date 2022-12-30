package kw.ic.backend.domain.likes.repository;

import kw.ic.backend.domain.likes.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface LikesRepository extends JpaRepository<Likes,Long> {

    public boolean existsByMemberIdAndRestaurantId(Long memberId, Long restaurantId);

    @Query(value = "select count(like_id) from likes where restaurant_id=:restaurantId", nativeQuery = true)
    long countLikesByRestaurantId(Long restaurantId);

    @Modifying(clearAutomatically = true)
    @Query(value = "delete from likes where member_id =:memberId and restaurant_id=:restaurantId", nativeQuery = true)
    public void deleteByMemberIdAndRestaurantId(@Param("memberId")Long memberId, @Param("restaurantId")Long restaurantId);
}

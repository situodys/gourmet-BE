package kw.ic.backend.likes.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.IntStream;
import kw.ic.backend.domain.likes.Likes;
import kw.ic.backend.domain.likes.dto.LikesRequest;
import kw.ic.backend.domain.likes.repository.LikesRepository;
import kw.ic.backend.domain.member.Member;
import kw.ic.backend.domain.member.dto.embbed.Authority;
import kw.ic.backend.domain.member.repository.MemberRepository;
import kw.ic.backend.domain.restaurant.dto.embbed.Address;
import kw.ic.backend.domain.restaurant.dto.embbed.RestaurantType;
import kw.ic.backend.domain.restaurant.dto.embbed.RunningTime;
import kw.ic.backend.domain.restaurant.dto.request.RestaurantRequest;
import kw.ic.backend.domain.restaurant.entity.Restaurant;
import kw.ic.backend.domain.restaurant.repository.RestaurantRepository;
import kw.ic.backend.global.config.JpaConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
public class LikesRepositoryTest {

    @Autowired
    private LikesRepository likesRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;

    @BeforeAll
    private void init() {
        IntStream.rangeClosed(1,3)
                .forEach(idx ->{
                    Member member = Member.builder()
                            .email("email")
                            .password("password")
                            .authority(Authority.ROLE_USER)
                            .build();
                    Restaurant restaurant = Restaurant.builder()
                            .type(RestaurantType.KOREAN)
                            .address(new Address("city", "street", "zipcode"))
                            .runningTime(new RunningTime(LocalDateTime.now(), LocalDateTime.now()))
                            .build();

                    memberRepository.save(member);
                    restaurantRepository.save(restaurant);
                });

    }

    @Test
    @DisplayName("식당 좋아요 확인")
    public void add() throws Exception {
        //given
        Likes likes = new LikesRequest(1L, 1L).toLikes();

        //when
        Likes result = likesRepository.save(likes);

        //then
        assertThat(result.getId()).isEqualTo(2L);
    }

    @Test
    @DisplayName("식당 좋아요 취소 확인")
    public void cancel() throws Exception {
        //given
        Likes likes = new LikesRequest(2L, 2L).toLikes();
        Likes createdLikes = likesRepository.save(likes);
        Long createdLikesId = createdLikes.getId();

        //when
        likesRepository.delete(createdLikes);
        Optional<Likes> result = likesRepository.findById(createdLikesId);

        //then
        assertThat(result.isPresent()).isEqualTo(false);
    }
}

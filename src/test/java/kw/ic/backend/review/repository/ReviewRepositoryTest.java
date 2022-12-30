package kw.ic.backend.review.repository;


import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import kw.ic.backend.domain.member.Member;
import kw.ic.backend.domain.member.dto.embbed.Authority;
import kw.ic.backend.domain.member.repository.MemberRepository;
import kw.ic.backend.domain.menu.Menu;
import kw.ic.backend.domain.menu.ReviewedMenu;
import kw.ic.backend.domain.menu.dto.SimpleMenu;
import kw.ic.backend.domain.menu.repository.MenuRepository;
import kw.ic.backend.domain.menu.repository.ReviewedMenuRepository;
import kw.ic.backend.domain.menu.service.ReviewedMenuService;
import kw.ic.backend.domain.restaurant.dto.embbed.Address;
import kw.ic.backend.domain.restaurant.dto.embbed.RestaurantType;
import kw.ic.backend.domain.restaurant.dto.embbed.RunningTime;
import kw.ic.backend.domain.restaurant.dto.response.RestaurantResponse;
import kw.ic.backend.domain.restaurant.entity.Restaurant;
import kw.ic.backend.domain.restaurant.repository.RestaurantRepository;
import kw.ic.backend.domain.review.dto.request.ReviewRequest;
import kw.ic.backend.domain.review.entity.Review;
import kw.ic.backend.domain.review.entity.Review.ReviewBuilder;
import kw.ic.backend.domain.review.repository.ReviewRepository;
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
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@ActiveProfiles("test")
@Import({JpaConfig.class,ReviewedMenuService.class})
@TestInstance(Lifecycle.PER_CLASS)
public class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private ReviewedMenuService reviewedMenuService;

    @BeforeAll
    private void init() {
        Member member = Member.builder()
                .email("email")
                .password("pw")
                .authority(Authority.ROLE_USER)
                .build();
        memberRepository.save(member);

        Restaurant restaurant = Restaurant.builder()
                .name("name")
                .description("description")
                .type(RestaurantType.KOREAN)
                .address(new Address("city1", "street1", "zipcode"))
                .runningTime(new RunningTime(LocalDateTime.now(), LocalDateTime.now()))
                .build();
        restaurantRepository.save(restaurant);

        for (int i = 0; i < 2; i++) {
            Menu menu = Menu.builder()
                    .menuName("menuName")
                    .price(1000)
                    .description("description")
                    .restaurant(restaurant)
                    .build();
            menuRepository.save(menu);
        }
    }

    @Test
    @DisplayName("리뷰 한건 정상 조회 확인")
    @Transactional
    public void findReview() throws Exception{
        //given
        Review review = Review.builder()
                .title("title3")
                .content("content3")
                .rating(3)
                .member(memberRepository.getReferenceById(1L))
                .restaurant(restaurantRepository.getReferenceById(1L))
                .build();

        reviewRepository.save(review);
        Long reviewId = review.getId();

        int registerCount = reviewedMenuService.registerAll(List.of(new SimpleMenu(1L, "김치찌개"),
                new SimpleMenu(2L, "된장찌개")), review);
        System.out.println(registerCount);

        reviewRepository.flush();
        //when
        Optional<Review> result = reviewRepository.findById(reviewId);
        //then
        assertThat(result.isPresent()).isEqualTo(true);
    }

    @Test
    @DisplayName("리뷰 정상 등록 확인")
    public void register() throws Exception {
        //given
        ReviewRequest request = new ReviewRequest(
                "title",
                "content",
                5,
                1L,
                1L,
                List.of(new SimpleMenu(1L, "김치찌개"),
                        new SimpleMenu(2L, "된장찌개"))
        );
        //when
        Review registeredReview = reviewRepository.save(request.toReview());

        //then
        assertThat(registeredReview.getTitle()).isEqualTo("title");
        assertThat(registeredReview.getContent()).isEqualTo("content");

//        assertThat(registeredReview.getMember().getEmail()).isEqualTo("email");
//        assertThat(registeredReview.getMember().getPassword()).isEqualTo("pw");
//        assertThat(registeredReview.getRestaurant().getName()).isEqualTo("name");
    }

    @Test
    public void delete() throws Exception{
        //given
        Review review = Review.builder()
                .title("title3")
                .content("content3")
                .rating(3)
                .member(memberRepository.getReferenceById(1L))
                .restaurant(restaurantRepository.getReferenceById(1L))
                .build();

        //when
        reviewRepository.save(review);
        reviewRepository.deleteById(review.getId());
        Optional<Review> result = reviewRepository.findById(review.getId());

        //then
        assertThat(result.isPresent()).isEqualTo(false);
    }
}

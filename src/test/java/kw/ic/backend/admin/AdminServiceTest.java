package kw.ic.backend.admin;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import kw.ic.backend.domain.member.Member;
import kw.ic.backend.domain.member.admin.service.AdminService;
import kw.ic.backend.domain.member.dto.embbed.Authority;
import kw.ic.backend.domain.member.repository.MemberRepository;
import kw.ic.backend.domain.menu.Menu;
import kw.ic.backend.domain.menu.repository.MenuRepository;
import kw.ic.backend.domain.notification.Notification;
import kw.ic.backend.domain.notification.repository.NotificationRepository;
import kw.ic.backend.domain.proposal.dto.embbed.Category;
import kw.ic.backend.domain.proposal.dto.request.ProposalRequest;
import kw.ic.backend.domain.proposal.repository.ProposalRepository;
import kw.ic.backend.domain.restaurant.dto.embbed.Address;
import kw.ic.backend.domain.restaurant.dto.embbed.RestaurantType;
import kw.ic.backend.domain.restaurant.dto.embbed.RunningTime;
import kw.ic.backend.domain.restaurant.entity.Restaurant;
import kw.ic.backend.domain.restaurant.repository.RestaurantRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
public class AdminServiceTest {

    @Autowired
    private AdminService adminService;
    @Autowired
    private ProposalRepository proposalRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private NotificationRepository notificationRepository;

    @BeforeAll
    private void init() {
//
//        notificationRepository.deleteAll();
//        proposalRepository.deleteAll();
//        menuRepository.deleteAll();
//        memberRepository.deleteAll();
//        restaurantRepository.deleteAll();
//
//        notificationRepository.flush();
//        proposalRepository.flush();
//        menuRepository.flush();
//        memberRepository.flush();
//        restaurantRepository.flush();

        Restaurant restaurant = Restaurant.builder()
                .name("restaurantName")
                .description("description")
                .type(RestaurantType.KOREAN)
                .address(new Address("city1", "street1", "zipcode"))
                .runningTime(new RunningTime(LocalDateTime.now(), LocalDateTime.now()))
                .build();

        restaurantRepository.save(restaurant);

        Member member = Member.builder()
                .authority(Authority.ROLE_ADMIN)
                .email("email")
                .password("test")
                .build();
        memberRepository.save(member);

        Menu menu = Menu.builder()
                .menuName("menu")
                .price(1000)
                .description("desc")
                .restaurant(restaurant)
                .build();
        menuRepository.save(menu);
        System.out.println("menuId: " + menu.getId());

    }

    @Test
    @Transactional
    public void applyMenuName() throws Exception {
        //given
        ProposalRequest proposalRequest = new ProposalRequest(
                1L, "title", Category.MENU_NAME, "메뉴이름 변경됨", 1L, 1L, 1L
        );
        //when
        adminService.apply(proposalRequest);

        menuRepository.flush();
        restaurantRepository.flush();
        notificationRepository.flush();
        proposalRepository.flush();

        //then
        List<Notification> ns = notificationRepository.findAll();
        Menu menu = menuRepository.findById(1L).get();
        assertThat(ns.size()).isEqualTo(1);
        assertThat(ns.get(0).getPreviousContent()).isEqualTo("menu");
        assertThat(ns.get(0).getUpdatedContent()).isEqualTo("메뉴이름 변경됨");

        assertThat(menu.getMenuName()).isEqualTo("메뉴이름 변경됨");
    }

    @Test
    @Transactional
    public void applyPrice() throws Exception {
        //given
        ProposalRequest proposalRequest = new ProposalRequest(
                1L, "title", Category.PRICE, "2000", 1L, 1L, 1L
        );
        //when
        adminService.apply(proposalRequest);

        menuRepository.flush();
        restaurantRepository.flush();
        notificationRepository.flush();
        proposalRepository.flush();

        //then
        List<Notification> ns = notificationRepository.findAll();
        Menu menu = menuRepository.findById(1L).get();
        assertThat(ns.size()).isEqualTo(1);
        assertThat(ns.get(0).getPreviousContent()).isEqualTo("menu1000");
        assertThat(ns.get(0).getUpdatedContent()).isEqualTo("2000");

        assertThat(menu.getPrice()).isEqualTo(2000);
    }

    @Test
    @Transactional
    public void applyOpenAt() throws Exception {
        //given
        ProposalRequest proposalRequest = new ProposalRequest(
                1L, "title", Category.OPEN_TIME, "11:11", 1L, 1L, 1L
        );
        String beforeTime = restaurantRepository.getReferenceById(1L)
                .getRunningTime().getOpenAt().format(DateTimeFormatter.ofPattern("HH:mm"));
        //when
        adminService.apply(proposalRequest);

        menuRepository.flush();
        restaurantRepository.flush();
        notificationRepository.flush();
        proposalRepository.flush();

        //then
        List<Notification> ns = notificationRepository.findAll();
        Restaurant restaurant = restaurantRepository.findById(1L).get();
        assertThat(ns.size()).isEqualTo(1);
        assertThat(ns.get(0).getPreviousContent()).isEqualTo(beforeTime);
        assertThat(ns.get(0).getUpdatedContent()).isEqualTo("11:11");

        System.out.println(restaurant.getRunningTime().getOpenAt());
    }
}

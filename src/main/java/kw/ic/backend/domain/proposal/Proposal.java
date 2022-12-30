package kw.ic.backend.domain.proposal;

import kw.ic.backend.domain.menu.Menu;
import kw.ic.backend.domain.proposal.dto.embbed.Category;
import kw.ic.backend.domain.member.Member;
import kw.ic.backend.domain.restaurant.entity.Restaurant;
import kw.ic.backend.global.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.util.Assert;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Proposal extends BaseEntity {

    private static final String STATUS_REFUSE = "refuse";
    private static final String STATUS_APPLY = "apply";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="proposal_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    private String content;

    @Column
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @Builder
    public Proposal(String title, Category category, String content, String status, Restaurant restaurant, Member member, Menu menu) {
        Assert.notNull(category, "category must not be empty");
        Assert.hasText(content, "requestContent must not be empty");

        this.title = title;
        this.category = category;
        this.content = content;
        this.status = status;
        this.restaurant = restaurant;
        this.member = member;
        this.menu = menu;
    }

    public void changeStatusToRefuse() {
        this.status = STATUS_REFUSE;
    }

    public void changeStatusTOApply() {
        this.status = STATUS_APPLY;
    }
}

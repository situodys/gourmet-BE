package kw.ic.backend.domain.review.entity;

import kw.ic.backend.domain.menu.ReviewedMenu;
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
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Integer rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    @OneToMany(mappedBy = "review", cascade = {CascadeType.REMOVE})
    private List<ReviewedMenu> reviewedMenus = new ArrayList<>();

    @Builder
    public Review(String title, String content, Integer rating, Member member, Restaurant restaurant, List<ReviewedMenu> reviewedMenus) {
        Assert.hasText(title,"title must not be empty");
        Assert.hasText(content,"content must not be empty");
        Assert.notNull(rating, "rating must not be empty");

        this.title=title;
        this.content = content;
        this.rating = rating;
        this.member = member;
        this.restaurant = restaurant;
        this.reviewedMenus = reviewedMenus;
    }
}

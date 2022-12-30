package kw.ic.backend.domain.menu;

import kw.ic.backend.domain.menu.Menu;
import kw.ic.backend.domain.review.entity.Review;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewedMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reviewed_menu_id")
    private Long id;

    @Column(name="menu_name")
    private String menuName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Menu menu;

    @Builder
    public ReviewedMenu(String menuName, Review review, Menu menu) {
        this.menuName = menuName;
        this.review=review;
        this.menu = menu;
    }
}

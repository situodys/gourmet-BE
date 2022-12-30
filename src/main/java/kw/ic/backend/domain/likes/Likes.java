package kw.ic.backend.domain.likes;

import kw.ic.backend.domain.member.Member;
import kw.ic.backend.domain.restaurant.entity.Restaurant;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity(name ="likes")
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Likes {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;

    @Builder
    public Likes(Restaurant restaurant, Member member) {
        this.restaurant = restaurant;
        this.member = member;
    }

}

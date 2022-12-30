package kw.ic.backend.domain.menu.repository;

import static kw.ic.backend.domain.menu.QMenu.menu;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.util.StringUtils;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import kw.ic.backend.domain.menu.Menu;
import kw.ic.backend.domain.menu.dto.request.MenuPageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class MenuRepositoryDSLImpl extends QuerydslRepositorySupport implements MenuRepositoryDSL {

    private final JPAQueryFactory jpaQueryFactory;
    private static final int NEED_CALCULATE = -1;

    @Autowired
    public MenuRepositoryDSLImpl(JPAQueryFactory jpaQueryFactory) {
        super(Menu.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Page<Menu> findMenusWithPagination(MenuPageRequest request) {

        JPAQuery<Menu> query = jpaQueryFactory
                .select(menu)
                .from(menu)
                .where(
                        menu.restaurant.id.eq(request.getRestaurantId()),
                        eqMenuName(request.getMenuName()),
                        ltEqPrice(request.getPrice()));

        long totalCount = calculateTotalCount(query, request);

        List<Menu> menus = this.getQuerydsl().applyPagination(request.getPageRequest(), query).fetch();
        return new PageImpl<>(menus, request.getPageRequest(), totalCount);
    }

    private long calculateTotalCount(JPAQuery<Menu> query, MenuPageRequest request) {
        if (request.getTotalCount() == NEED_CALCULATE) {
            return query.fetch().size();
        }
        return request.getTotalCount();
    }

    private BooleanExpression eqMenuName(String menuName) {
        if (StringUtils.isNullOrEmpty(menuName)) {
            return null;
        }
        return menu.menuName.contains(menuName);
    }

    private BooleanExpression ltEqPrice(Integer price) {
        if (price == null) {
            return null;
        }
        return menu.price.loe(price);
    }
}

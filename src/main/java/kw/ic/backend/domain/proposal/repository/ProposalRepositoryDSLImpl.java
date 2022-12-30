package kw.ic.backend.domain.proposal.repository;

import static kw.ic.backend.domain.member.QMember.member;
import static kw.ic.backend.domain.menu.QMenu.menu;
import static kw.ic.backend.domain.proposal.QProposal.proposal;
import static kw.ic.backend.domain.restaurant.entity.QRestaurant.restaurant;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import kw.ic.backend.domain.proposal.Proposal;
import kw.ic.backend.domain.proposal.dto.request.ProposalPageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class ProposalRepositoryDSLImpl extends QuerydslRepositorySupport implements
        ProposalRepositoryDSL {

    private static final int NEED_CALCULATE =-1;
    private final JPAQueryFactory jpaQueryFactory;

    public ProposalRepositoryDSLImpl(JPAQueryFactory jpaQueryFactory) {
        super(Proposal.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Page<Proposal> findProposals(ProposalPageRequest request) {

        Long totalCount=request.getTotalCount();

        JPAQuery<Proposal> query = jpaQueryFactory.select(proposal)
                .from(proposal)
                .leftJoin(proposal.restaurant, restaurant)
                .fetchJoin().
                leftJoin(proposal.member, member)
                .fetchJoin()
                .leftJoin(proposal.menu, menu)
                .fetchJoin();

        if (totalCount == NEED_CALCULATE) {
            totalCount = (long)query.fetch().size();
        }

        List<Proposal> proposals = this.getQuerydsl().applyPagination(request.getPageRequest(), query).fetch();
        return new PageImpl<>(proposals, request.getPageRequest(), totalCount);
    }

    @Override
    public Page<Proposal> findProposalsByRestaurantId(Long restaurantId, ProposalPageRequest request) {

        Long totalCount=request.getTotalCount();

        JPAQuery<Proposal> query = jpaQueryFactory.select(proposal)
                .from(proposal)
                .leftJoin(proposal.restaurant, restaurant)
                .fetchJoin().
                leftJoin(proposal.member, member)
                .fetchJoin()
                .leftJoin(proposal.menu, menu)
                .fetchJoin()
                .where(
                        proposal.restaurant.id.eq(restaurantId)
                );

        if (totalCount == NEED_CALCULATE) {
            totalCount = (long)query.fetch().size();
        }

        List<Proposal> proposals = this.getQuerydsl().applyPagination(request.getPageRequest(), query).fetch();
        return new PageImpl<>(proposals, request.getPageRequest(), totalCount);
    }
}

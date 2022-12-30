package kw.ic.backend.domain.member.admin.service;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import kw.ic.backend.domain.menu.Menu;
import kw.ic.backend.domain.menu.repository.MenuRepository;
import kw.ic.backend.domain.notification.Notification;
import kw.ic.backend.domain.notification.repository.NotificationRepository;
import kw.ic.backend.domain.proposal.Proposal;
import kw.ic.backend.domain.proposal.dto.embbed.Category;
import kw.ic.backend.domain.proposal.dto.request.ProposalRequest;
import kw.ic.backend.domain.proposal.repository.ProposalRepository;
import kw.ic.backend.domain.restaurant.entity.Restaurant;
import kw.ic.backend.domain.restaurant.repository.RestaurantRepository;
import kw.ic.backend.global.exception.CustomException;
import kw.ic.backend.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {

    private final ProposalRepository proposalRepository;
    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;
    private final NotificationRepository notificationRepository;

    public void apply(ProposalRequest request) {
        Category category = request.getCategory();

        changeStatusToApply(request);
        if (category.equals(Category.PRICE)) {
            Menu menu = menuRepository.findById(request.getMenuId())
                    .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));
            modifyPrice(request, menu);
            return;
        }
        if (category.equals(Category.MENU_NAME)) {
            Menu menu = menuRepository.findById(request.getMenuId())
                    .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));
            modifyMenuName(request, menu);
            return;
        }
        if (category.equals(Category.OPEN_TIME)) {
            modifyOpenTime(request);
            return;
        }
        if (category.equals(Category.CLOSE_TIME)) {
            modifyCloseTime(request);
            return;
        }
    }

    public void refuse(ProposalRequest request) {
        Proposal proposal = proposalRepository.findById(request.getProposalId())
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));

        proposal.changeStatusToRefuse();

        proposalRepository.save(proposal);
        return;
    }

    private void changeStatusToApply(ProposalRequest request) {
        Proposal proposal = proposalRepository.findById(request.getProposalId())
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));

        proposal.changeStatusTOApply();

        proposalRepository.save(proposal);
        return;
    }

    private void modifyPrice(ProposalRequest request, Menu menu) {
        String previousContent = menu.getMenuName();
        previousContent = previousContent + ","+String.valueOf(menu.getPrice());
        menu.changePrice(request.getContent());
        menuRepository.save(menu);
        registerNotification(request, previousContent);
        return;
    }

    private void modifyMenuName(ProposalRequest request, Menu menu) {
        String previousContent;
        previousContent = menu.getMenuName();
        menu.changeMenuName(request.getContent());
        menuRepository.save(menu);
        registerNotification(request, previousContent);
        return;
    }

    private void modifyOpenTime(ProposalRequest request) {
        String previousContent;
        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId())
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));
        previousContent = restaurant.getRunningTime().getOpenAt()
                .format(DateTimeFormatter.ofPattern("HH:mm"));

        restaurant.changeOpenAt(request.getContent().split(":"));
        restaurantRepository.save(restaurant);

        registerNotification(request, previousContent);
        return;
    }

    private void modifyCloseTime(ProposalRequest request) {
        String previousContent;
        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId())
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND));
        previousContent = restaurant.getRunningTime().getCloseAt()
                .format(DateTimeFormatter.ofPattern("HH:mm"));

        restaurant.changeCloseAt(request.getContent().split(":"));
        restaurantRepository.save(restaurant);

        registerNotification(request, previousContent);
        return;
    }

    private void registerNotification(ProposalRequest request, String previousContent) {
        notificationRepository.save(
                Notification.builder()
                        .category(request.getCategory())
                        .previousContent(previousContent)
                        .updatedContent(request.getContent())
                        .restaurant(restaurantRepository.getReferenceById(request.getRestaurantId()))
                        .build()
        );
    }
}

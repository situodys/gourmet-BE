package kw.ic.backend.domain.menu.service;

import java.util.List;
import java.util.stream.Collectors;
import kw.ic.backend.domain.menu.ReviewedMenu;
import kw.ic.backend.domain.menu.dto.SimpleMenu;
import kw.ic.backend.domain.menu.repository.MenuRepository;
import kw.ic.backend.domain.menu.repository.ReviewedMenuRepository;
import kw.ic.backend.domain.review.entity.Review;
import kw.ic.backend.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ReviewedMenuService {

    private final ReviewedMenuRepository reviewedMenuRepository;
    private final MenuRepository menuRepository;

     public int registerAll(List<SimpleMenu> simpleMenus, Review review) {
        List<ReviewedMenu> reviewedMenus = simpleMenus.stream()
                .map(simpleMenu -> ReviewedMenu.builder()
                        .menuName(simpleMenu.getMenuName())
                        .review(review)
                        .menu(menuRepository.getReferenceById(simpleMenu.getMenuId()))
                        .build())
                .collect(Collectors.toUnmodifiableList());

         List<ReviewedMenu> result = reviewedMenuRepository.saveAll(reviewedMenus);
         return result.size();
     }

}

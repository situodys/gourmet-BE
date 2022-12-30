package kw.ic.backend.domain.likes.service;

import kw.ic.backend.domain.likes.Likes;
import kw.ic.backend.domain.likes.dto.LikesRequest;
import kw.ic.backend.domain.likes.repository.LikesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class LikesService {

    private final LikesRepository likesRepository;

    public Long add(LikesRequest likesRequest) {
        Likes likes = likesRepository.save(likesRequest.toLikes());

        return likesRepository.countLikesByRestaurantId(likesRequest.getRestaurantId());
    }

    public Long cancel(LikesRequest likesRequest) {
        likesRepository.deleteByMemberIdAndRestaurantId(likesRequest.getMemberId(), likesRequest.getRestaurantId());
        return likesRepository.countLikesByRestaurantId(likesRequest.getRestaurantId());

    }
}

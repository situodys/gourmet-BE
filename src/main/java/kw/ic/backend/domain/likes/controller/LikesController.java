package kw.ic.backend.domain.likes.controller;

import kw.ic.backend.domain.likes.dto.LikesRequest;
import kw.ic.backend.domain.likes.service.LikesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/likes")
public class LikesController {

    private final LikesService likesService;

    @PostMapping("/add")
    ResponseEntity<Long> add(@RequestBody LikesRequest likesRequest) {
        log.info("memberId {} like to restaurantId {}");

        Long likeCount = likesService.add(likesRequest);

        return ResponseEntity.ok(likeCount);
    }

    @PostMapping("/cancel")
    ResponseEntity<Long> cancel(@RequestBody LikesRequest likesRequest) {
        log.info("memberId {} cancel like to restaurantId {}");

        Long likeCount = likesService.cancel(likesRequest);

        return ResponseEntity.ok(likeCount);
    }
}

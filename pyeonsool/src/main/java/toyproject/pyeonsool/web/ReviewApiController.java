package toyproject.pyeonsool.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import toyproject.pyeonsool.LoginMember;
import toyproject.pyeonsool.SessionConst;
import toyproject.pyeonsool.domain.RecommendStatus;
import toyproject.pyeonsool.service.ReviewService;

import static java.util.Objects.isNull;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewApiController {

    private final ReviewService reviewService;

    @PostMapping("/{reviewId}/recommend")
    public ResponseEntity<Void> recommendReview(
            @PathVariable Long reviewId,
            @SessionAttribute(value = SessionConst.LOGIN_MEMBER, required = false) LoginMember loginMember) {

        if (isNull(loginMember)) {
            // TODO advice 클래스 생성 후 예외 통합 관리
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        reviewService.recommendReview(loginMember.getId(), reviewId, RecommendStatus.LIKE);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{reviewId}/not-recommend")
    public ResponseEntity<Void> notRecommendReview(
            @PathVariable Long reviewId,
            @SessionAttribute(value = SessionConst.LOGIN_MEMBER, required = false) LoginMember loginMember) {

        if (isNull(loginMember)) {
            // TODO advice 클래스 생성 후 예외 통합 관리
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        reviewService.recommendReview(loginMember.getId(), reviewId, RecommendStatus.DISLIKE);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{reviewId}/recommend")
    public ResponseEntity<Void> cancelRecommendation(
            @PathVariable Long reviewId,
            @SessionAttribute(value = SessionConst.LOGIN_MEMBER, required = false) LoginMember loginMember) {

        if (isNull(loginMember)) {
            // TODO advice 클래스 생성 후 예외 통합 관리
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        reviewService.cancelRecommendation(loginMember.getId(), reviewId, RecommendStatus.LIKE);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{reviewId}/not-recommend")
    public ResponseEntity<Void> cancelNotRecommendation(
            @PathVariable Long reviewId,
            @SessionAttribute(value = SessionConst.LOGIN_MEMBER, required = false) LoginMember loginMember) {

        if (isNull(loginMember)) {
            // TODO advice 클래스 생성 후 예외 통합 관리
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        reviewService.cancelRecommendation(loginMember.getId(), reviewId, RecommendStatus.DISLIKE);

        return ResponseEntity.ok().build();
    }
}

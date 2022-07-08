package toyproject.pyeonsool.review.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import toyproject.pyeonsool.common.LoginMember;
import toyproject.pyeonsool.common.SessionConst;
import toyproject.pyeonsool.domain.RecommendStatus;
import toyproject.pyeonsool.review.sevice.ReviewService;

import static java.util.Objects.isNull;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewApiController {

    private final ReviewService reviewService;

    @PostMapping("/add")
    public ResponseEntity<Void> addReview(
            @SessionAttribute(value = SessionConst.LOGIN_MEMBER, required = false) LoginMember loginMember,
            @RequestBody ReviewSaveRequest reviewSaveRequest) {
        if (isNull(loginMember)) {
            // TODO advice 클래스 생성 후 예외 통합 관리
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        // TODO reviewSaveRequest 필드값 예외처리 필요

        reviewService.addReview(loginMember.getId(), reviewSaveRequest.getAlcoholId(), reviewSaveRequest.getGrade(),
                reviewSaveRequest.getContent());

        return ResponseEntity.ok().build();
    }

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

    //리뷰 삭제 컨트롤러
    @DeleteMapping("/{reviewId}/delete")
    public ResponseEntity<Void> deleteReview(
            @PathVariable Long reviewId,
            @SessionAttribute(value = SessionConst.LOGIN_MEMBER, required = false) LoginMember loginMember) {

        if (isNull(loginMember)) {
            // TODO advice 클래스 생성 후 예외 통합 관리
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        reviewService.deleteReview(reviewId);

        return ResponseEntity.ok().build();
    }
}

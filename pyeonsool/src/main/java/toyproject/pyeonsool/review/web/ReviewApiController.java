package toyproject.pyeonsool.review.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import toyproject.pyeonsool.common.LoginMember;
import toyproject.pyeonsool.common.SessionConst;
import toyproject.pyeonsool.common.exception.api.httpstatus.ApiExceptionType;
import toyproject.pyeonsool.domain.RecommendStatus;
import toyproject.pyeonsool.review.sevice.ReviewService;

import static java.util.Objects.isNull;
import static toyproject.pyeonsool.common.exception.api.httpstatus.ApiExceptionType.*;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewApiController {

    private final ReviewService reviewService;

    @PostMapping("/add")
    public ResponseEntity<Void> addReview(
            @SessionAttribute(value = SessionConst.LOGIN_MEMBER, required = false) LoginMember loginMember,
            @RequestBody ReviewSaveRequest reviewSaveRequest) {

        validateLogin(loginMember);
        validateReviewSaveRequest(reviewSaveRequest);

        reviewService.addReview(loginMember.getId(), reviewSaveRequest.getAlcoholId(), reviewSaveRequest.getGrade(),
                reviewSaveRequest.getContent());

        return ResponseEntity.ok().build();
    }

    private void validateLogin(LoginMember loginMember) {
        if (isNull(loginMember)) {
            throw MUST_LOGIN.getException();
        }
    }

    private void validateReviewSaveRequest(ReviewSaveRequest reviewSaveRequest) {
        if (isNull(reviewSaveRequest.getAlcoholId())) {
            throw REQUIRED_ALCOHOL_ID.getException();
        }

        if (isNull(reviewSaveRequest.getGrade())) {
            throw REQUIRED_GRADE.getException();
        }

        if (!StringUtils.hasText(reviewSaveRequest.getContent())) {
            throw NON_BLANK_REVIEW.getException();
        }

        if (reviewSaveRequest.getContent().length() > 300) {
            throw MAX_LENGTH_REVIEW.getException();
        }
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

package toyproject.pyeonsool.review.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import toyproject.pyeonsool.common.LoginMember;
import toyproject.pyeonsool.common.SessionConst;
import toyproject.pyeonsool.recommendedreview.domain.RecommendStatus;
import toyproject.pyeonsool.review.sevice.ReviewService;

import javax.validation.Valid;

import static java.util.Objects.isNull;
import static toyproject.pyeonsool.common.exception.api.ApiExceptionType.*;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewApiController {

    private final ReviewService reviewService;

    @PostMapping("/add")
    public ResponseEntity<Void> addReview(
            @SessionAttribute(value = SessionConst.LOGIN_MEMBER, required = false) LoginMember loginMember,
            @RequestBody @Valid ReviewSaveRequest reviewSaveRequest) {

        reviewService.addReview(loginMember.getId(), reviewSaveRequest.getAlcoholId(), reviewSaveRequest.getGrade(),
                reviewSaveRequest.getContent());

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{reviewId}/edit")
    public ResponseEntity<Void> editReview(
            @PathVariable Long reviewId,
            @SessionAttribute(value = SessionConst.LOGIN_MEMBER, required = false) LoginMember loginMember,
            @RequestBody @Valid ReviewEditRequest reviewEditRequest) {

        validateReviewId(reviewId);

        reviewService.editReview(
                reviewId, loginMember.getId(), reviewEditRequest.getGrade(), reviewEditRequest.getContent());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{reviewId}/recommend")
    public ResponseEntity<Void> recommendReview(
            @PathVariable Long reviewId,
            @SessionAttribute(value = SessionConst.LOGIN_MEMBER, required = false) LoginMember loginMember) {

        validateReviewId(reviewId);
        reviewService.recommendReview(loginMember.getId(), reviewId);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{reviewId}/not-recommend")
    public ResponseEntity<Void> notRecommendReview(
            @PathVariable Long reviewId,
            @SessionAttribute(value = SessionConst.LOGIN_MEMBER, required = false) LoginMember loginMember) {

        validateReviewId(reviewId);
        reviewService.notRecommendReview(loginMember.getId(), reviewId);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{reviewId}/recommend")
    public ResponseEntity<Void> cancelRecommendation(
            @PathVariable Long reviewId,
            @SessionAttribute(value = SessionConst.LOGIN_MEMBER, required = false) LoginMember loginMember) {

        validateReviewId(reviewId);
        reviewService.cancelRecommendation(loginMember.getId(), reviewId, RecommendStatus.LIKE);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{reviewId}/not-recommend")
    public ResponseEntity<Void> cancelNotRecommendation(
            @PathVariable Long reviewId,
            @SessionAttribute(value = SessionConst.LOGIN_MEMBER, required = false) LoginMember loginMember) {

        validateReviewId(reviewId);
        reviewService.cancelRecommendation(loginMember.getId(), reviewId, RecommendStatus.DISLIKE);

        return ResponseEntity.ok().build();
    }

    //리뷰 삭제 컨트롤러
    @DeleteMapping("/{reviewId}/delete")
    public ResponseEntity<Void> deleteReview(
            @PathVariable Long reviewId,
            @SessionAttribute(value = SessionConst.LOGIN_MEMBER, required = false) LoginMember loginMember) {

        validateReviewId(reviewId);
        reviewService.deleteReview(reviewId);

        return ResponseEntity.ok().build();
    }

    private void validateReviewId(Long reviewId) {
        if (reviewId <= 0) {
            throw MUST_BE_POSITIVE_REVIEW_ID.getException();
        }
    }
}

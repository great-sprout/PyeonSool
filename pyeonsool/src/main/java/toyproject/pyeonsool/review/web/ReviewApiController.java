package toyproject.pyeonsool.review.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import toyproject.pyeonsool.common.LoginMember;
import toyproject.pyeonsool.common.SessionConst;
import toyproject.pyeonsool.domain.RecommendStatus;
import toyproject.pyeonsool.review.sevice.ReviewService;

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
            @RequestBody ReviewSaveRequest reviewSaveRequest) {

        validateLogin(loginMember);
        validateReviewSaveRequest(reviewSaveRequest);

        reviewService.addReview(loginMember.getId(), reviewSaveRequest.getAlcoholId(), reviewSaveRequest.getGrade(),
                reviewSaveRequest.getContent());

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{reviewId}/edit")
    public ResponseEntity<Void> editReview(
            @PathVariable Long reviewId,
            @SessionAttribute(value = SessionConst.LOGIN_MEMBER, required = false) LoginMember loginMember,
            @RequestBody ReviewEditRequest reviewEditRequest) {

        validateLogin(loginMember);
        validateReviewId(reviewId);
        validateReviewEditRequest(reviewEditRequest);

        reviewService.editReview(
                reviewId, loginMember.getId(), reviewEditRequest.getGrade(), reviewEditRequest.getContent());

        return ResponseEntity.ok().build();
    }

    private void validateLogin(LoginMember loginMember) {
        if (isNull(loginMember)) {
            throw MUST_LOGIN.getException();
        }
    }

    private void validateReviewEditRequest(ReviewEditRequest reviewEditRequest) {
        validateGrade(reviewEditRequest.getGrade());
        validateReviewContent(reviewEditRequest.getContent());
    }

    private void validateReviewSaveRequest(ReviewSaveRequest reviewSaveRequest) {
        validateAlcoholId(reviewSaveRequest.getAlcoholId());
        validateGrade(reviewSaveRequest.getGrade());
        validateReviewContent(reviewSaveRequest.getContent());
    }

    private void validateAlcoholId(Long alcoholId) {
        if (isNull(alcoholId)) {
            throw REQUIRED_ALCOHOL_ID.getException();
        }
    }

    private void validateReviewContent(String content) {
        if (!StringUtils.hasText(content)) {
            throw NON_BLANK_REVIEW.getException();
        } else if (content.length() > 300) {
            throw MAX_LENGTH_REVIEW.getException();
        }
    }

    private void validateGrade(Byte grade) {
        if (isNull(grade)) {
            throw REQUIRED_GRADE.getException();
        } else if (grade < 1 || grade > 5) {
            throw LIMITED_RANGE_GRADE.getException();
        }
    }

    private void validateReviewId(Long reviewId) {
        if (isNull(reviewId)) {
            throw REQUIRED_REVIEW_ID.getException();
        }
    }

    @PostMapping("/{reviewId}/recommend")
    public ResponseEntity<Void> recommendReview(
            @PathVariable Long reviewId,
            @SessionAttribute(value = SessionConst.LOGIN_MEMBER, required = false) LoginMember loginMember) {

        validateLogin(loginMember);
        validateReviewId(reviewId);
        reviewService.recommendReview(loginMember.getId(), reviewId);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{reviewId}/not-recommend")
    public ResponseEntity<Void> notRecommendReview(
            @PathVariable Long reviewId,
            @SessionAttribute(value = SessionConst.LOGIN_MEMBER, required = false) LoginMember loginMember) {

        validateLogin(loginMember);
        validateReviewId(reviewId);
        reviewService.notRecommendReview(loginMember.getId(), reviewId);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{reviewId}/recommend")
    public ResponseEntity<Void> cancelRecommendation(
            @PathVariable Long reviewId,
            @SessionAttribute(value = SessionConst.LOGIN_MEMBER, required = false) LoginMember loginMember) {

        validateLogin(loginMember);
        validateReviewId(reviewId);
        reviewService.cancelRecommendation(loginMember.getId(), reviewId, RecommendStatus.LIKE);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{reviewId}/not-recommend")
    public ResponseEntity<Void> cancelNotRecommendation(
            @PathVariable Long reviewId,
            @SessionAttribute(value = SessionConst.LOGIN_MEMBER, required = false) LoginMember loginMember) {

        validateLogin(loginMember);
        validateReviewId(reviewId);
        reviewService.cancelRecommendation(loginMember.getId(), reviewId, RecommendStatus.DISLIKE);

        return ResponseEntity.ok().build();
    }

    //리뷰 삭제 컨트롤러
    @DeleteMapping("/{reviewId}/delete")
    public ResponseEntity<Void> deleteReview(
            @PathVariable Long reviewId,
            @SessionAttribute(value = SessionConst.LOGIN_MEMBER, required = false) LoginMember loginMember) {

        validateLogin(loginMember);
        validateReviewId(reviewId);
        reviewService.deleteReview(reviewId);

        return ResponseEntity.ok().build();
    }
}

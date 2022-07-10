package toyproject.pyeonsool.review.sevice;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import toyproject.pyeonsool.common.FileManager;
import toyproject.pyeonsool.alcohol.repository.AlcoholRepository;
import toyproject.pyeonsool.domain.*;
import toyproject.pyeonsool.member.repository.MemberRepository;
import toyproject.pyeonsool.recommendedreview.repository.RecommendedReviewRepository;
import toyproject.pyeonsool.review.repository.ReviewImageDto;
import toyproject.pyeonsool.review.repository.ReviewRepository;

import javax.transaction.Transactional;

import java.util.Optional;

import static java.util.Objects.*;
import static toyproject.pyeonsool.common.exception.api.ApiExceptionType.*;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final AlcoholRepository alcoholRepository;
    private final MemberRepository memberRepository;
    private final RecommendedReviewRepository recommendedReviewRepository;
    private final FileManager fileManager;

    public Long addReview(long memberId, long alcoholId, byte grade, String content) {
        return reviewRepository
                .save(new Review(getMemberOrElseThrow(memberId), getAlcoholOrElseThrow(alcoholId), grade, content))
                .getId();
    }

    private Member getMemberOrElseThrow(long memberId) {
        return memberRepository.findById(memberId).orElseThrow(NOT_EXIST_MEMBER::getException);
    }

    private Alcohol getAlcoholOrElseThrow(long alcoholId) {
        return alcoholRepository.findById(alcoholId).orElseThrow(NOT_EXIST_ALCOHOL::getException);
    }

    public void editReview(long reviewId, long memberId, byte grade, String content) {
        Review review = getReviewOrElseThrow(reviewId);

        validateReviewAccess(memberId, review);

        review.changeGrade(grade);
        review.changeContent(content);
    }

    private Review getReviewOrElseThrow(long reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(NOT_EXIST_REVIEW::getException);
    }

    private void validateReviewAccess(Long memberId, Review review) {
        if (!review.getMember().getId().equals(memberId)) {
            throw FORBIDDEN_REVIEW.getException();
        }
    }

    public void deleteReview(long reviewId) {
        reviewRepository.findById(reviewId).ifPresent(reviewRepository::delete);
    }

    public Page<ReviewDto> getReviewPage(Pageable pageable, long alcoholId, Long memberId) {
        return reviewRepository.findReviewsByAlcohol(getAlcoholOrElseThrow(alcoholId), pageable)
                .map(review -> ReviewDto.of(review, getMyRecommendStatus(review, memberId)));
    }

    private RecommendStatus getMyRecommendStatus(Review review, Long memberId) {
        if (isNull(memberId)) {
            return RecommendStatus.NORMAL;
        }
        Member member = memberRepository.findById(memberId).orElse(null);
        if (isNull(member)) {
            return RecommendStatus.NORMAL;
        }

        RecommendedReview recommendedReview =
                recommendedReviewRepository.findByMemberAndReview(member, review).orElse(null);

        if (isNull(recommendedReview)) {
            return RecommendStatus.NORMAL;
        }

        return recommendedReview.getStatus();
    }

    public Long recommendReview(long memberId, long reviewId, RecommendStatus status) {
        Review review = getReviewOrElseThrow(reviewId);
        Member member = getMemberOrElseThrow(memberId);

        RecommendedReview recommendedReview = recommendedReviewRepository.findByMemberAndReview(member, review)
                .orElseGet(() -> new RecommendedReview(member, review, status));

        if (isNull(recommendedReview.getId())) {
            recommendedReviewRepository.save(recommendedReview);
        } else {
            recommendedReview.changeStatus(status);
            review.minusNotRecommendCount();
        }

        review.plusRecommendCount();

        return recommendedReview.getId();
    }

    public Long notRecommendReview(long memberId, long reviewId, RecommendStatus status) {
        Review review = getReviewOrElseThrow(reviewId);
        Member member = getMemberOrElseThrow(memberId);

        RecommendedReview recommendedReview = recommendedReviewRepository.findByMemberAndReview(member, review)
                .orElseGet(() -> new RecommendedReview(member, review, status));

        if (isNull(recommendedReview.getId())) {
            recommendedReviewRepository.save(recommendedReview);
        } else {
            recommendedReview.changeStatus(status);
            review.minusRecommendCount();
        }

        review.plusNotRecommendCount();

        return recommendedReview.getId();
    }

    public void cancelRecommendation(long memberId, long reviewId, RecommendStatus status) {
        Review review = getReviewOrElseThrow(reviewId);

        recommendedReviewRepository.findByMemberAndReviewAndStatus(getMemberOrElseThrow(memberId), review, status)
                .ifPresent(recommendedReview -> {
                    recommendedReviewRepository.delete(recommendedReview);

                    if (status == RecommendStatus.LIKE) {
                        review.minusRecommendCount();
                    } else {
                        review.minusNotRecommendCount();
                    }
                });
    }

    //마이페이지 내 리뷰 리스트 서비스
    public Page<ReviewImagePathDto> getReviewImagePathPage(Long memberId, Pageable pageable) {
        return reviewRepository.getReviewImage(memberId, pageable)
                .map(reviewImageDto -> ReviewImagePathDto.of(reviewImageDto, getAlcoholImagePath(reviewImageDto)));
    }

    private String getAlcoholImagePath(ReviewImageDto reviewImageDto) {
        return fileManager.getAlcoholImagePath(reviewImageDto.getType(), reviewImageDto.getFileName());
    }
}

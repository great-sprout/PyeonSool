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
        return reviewRepository.save(
                new Review(getMemberOrElseThrow(memberId), getAlcoholOrElseThrow(alcoholId), grade, content))
                .getId();
    }

    private Alcohol getAlcoholOrElseThrow(long alcoholId) {
        return alcoholRepository.findById(alcoholId).orElseThrow(NOT_EXIST_ALCOHOL::getException);
    }

    private Member getMemberOrElseThrow(long memberId) {
        return memberRepository.findById(memberId).orElseThrow(NOT_EXIST_MEMBER::getException);
    }

    //리뷰 수정
    public void editReview(long reviewId, byte grade, String content) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 리뷰입니다."));

        // TODO 술, 회원 예외처리 필요
        // save함수는 id를 전달하지 않으면 insert를 해준다
        // save함수는 id를 전달하면 해당 id에 대한 데이터가 있으면 update를 해준다
        // save함수는 id를 전달하면 해당 id에 대한 데이터가 없으면 insert를 한다.

        //유지할 내용들
        /*review.getMember();
        review.getAlcohol();
        review.getRecommendCount();
        review.getNotRecommendCount();*/

        review.changeGrade(grade);
        review.changeContent(content);


    }

    //리뷰 삭제
    public void deleteReview(long reviewId) {
        reviewRepository.findById(reviewId).ifPresent(review -> reviewRepository.delete(review));
    }

    public Page<ReviewDto> getReviewPage(Pageable pageable, Long alcoholId, Long memberId) {
        Alcohol alcohol = alcoholRepository.findById(alcoholId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 술입니다."));

        return reviewRepository.findReviewsByAlcohol(alcohol, pageable)
                .map(review -> ReviewDto.of(review, getMyRecommendStatus(review, getMember(memberId))));
    }

    private RecommendStatus getMyRecommendStatus(Review review, Member member) {
        if (isNull(member)) {
            return null;
        }

        Optional<RecommendedReview> optionalRecommendedReview =
                recommendedReviewRepository.findByMemberAndReview(member, review);

        if (optionalRecommendedReview.isEmpty()) {
            return null;
        }

        return optionalRecommendedReview.get().getStatus();
    }

    private Member getMember(Long memberId) {
        if (isNull(memberId)) {
            return null;
        }

        return memberRepository.findById(memberId).orElse(null);
    }

    public Long recommendReview(Long memberId, Long reviewId, RecommendStatus status) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 리뷰입니다."));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 회원입니다."));
        // TODO 술, 회원 예외처리 필요

        RecommendedReview recommendedReview = recommendedReviewRepository.findByMemberAndReview(member, review)
                .orElseGet(() -> new RecommendedReview(member, review, status));

        if (isNull(recommendedReview.getId())) {
            recommendedReviewRepository.save(recommendedReview);
        } else {
            recommendedReview.changeStatus(status);
            if (status == RecommendStatus.LIKE) {
                review.minusNotRecommendCount();
            } else {
                review.minusRecommendCount();
            }
        }

        if (status == RecommendStatus.LIKE) {
            review.plusRecommendCount();
        } else {
            review.plusNotRecommendCount();
        }

        return recommendedReview.getId();
    }

    public void cancelRecommendation(Long memberId, Long reviewId, RecommendStatus status) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 리뷰입니다."));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 회원입니다."));
        // TODO 술, 회원 예외처리 필요

        recommendedReviewRepository.findByMemberAndReviewAndStatus(member, review, status)
                .ifPresent(entity -> {
                    recommendedReviewRepository.delete(entity);
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

package toyproject.pyeonsool.alcohol.sevice;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import toyproject.pyeonsool.alcohol.domain.Alcohol;
import toyproject.pyeonsool.alcohol.domain.AlcoholType;
import toyproject.pyeonsool.common.FileManager;
import toyproject.pyeonsool.alcohol.repository.AlcoholRepository;
import toyproject.pyeonsool.alcohol.repository.AlcoholSearchConditionDto;
import toyproject.pyeonsool.alcoholkeyword.repository.AlcoholKeywordRepository;
import toyproject.pyeonsool.member.domain.Member;
import toyproject.pyeonsool.member.repository.MemberRepository;
import toyproject.pyeonsool.preferredalcohol.domain.PreferredAlcohol;
import toyproject.pyeonsool.preferredalcohol.repository.PreferredAlcoholRepository;
import toyproject.pyeonsool.review.repository.ReviewRepository;
import toyproject.pyeonsool.vendor.repository.VendorRepository;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.*;
import static toyproject.pyeonsool.common.exception.api.ApiExceptionType.*;

@Service
@RequiredArgsConstructor
@Transactional
public class AlcoholService {

    private final AlcoholRepository alcoholRepository;
    private final PreferredAlcoholRepository preferredAlcoholRepository;
    private final MemberRepository memberRepository;
    private final AlcoholKeywordRepository alcoholKeywordRepository;
    private final VendorRepository vendorRepository;
    private final ReviewRepository reviewRepository;
    private final FileManager fileManager;


    public AlcoholDetailsDto getAlcoholDetails(long alcoholId, Long memberId) {
        Alcohol alcohol = getAlcoholOrElseThrow(alcoholId);

        return AlcoholDetailsDto.of(alcohol,
                getAlcoholImagePath(alcohol),
                getTotalGrade(alcoholId),
                getAlcoholKeywords(alcoholId),
                vendorRepository.getAlcoholVendors(alcoholId),
                likeCurrentAlcohol(alcohol, memberId));
    }

    private Alcohol getAlcoholOrElseThrow(long alcoholId) {
        return alcoholRepository.findById(alcoholId).orElseThrow(NOT_EXIST_ALCOHOL::getException);
    }

    private String getTotalGrade(long alcoholId) {
        List<Byte> reviewGrades = reviewRepository.getReviewGrades(alcoholId);

        if (reviewGrades.size() != 0) {
            long gradeSum = reviewGrades.stream().mapToLong(rating -> rating).sum();
            return String.format("%.1f", (double) gradeSum / reviewGrades.size());
        }

        return "-";
    }

    private List<String> getAlcoholKeywords(Long alcoholId) {
        List<String> alcoholKeywords = new ArrayList<>();
        Map<String, String> keywordMap = createKeywordMap();
        for (String keyword : alcoholKeywordRepository.getAlcoholKeywords(alcoholId)) {
            alcoholKeywords.add(keywordMap.get(keyword));
        }
        return alcoholKeywords;
    }

    public static Map<String, String> createKeywordMap() {
        Map<String, String> keywordMap = new HashMap<>();
        keywordMap.put("sweet", "?????????");
        keywordMap.put("clear", "?????????");
        keywordMap.put("cool", "?????????");
        keywordMap.put("heavy", "?????????");
        keywordMap.put("light", "?????????");
        keywordMap.put("nutty", "?????????");
        keywordMap.put("fresh", "?????????");
        keywordMap.put("flower", "??????");
        keywordMap.put("bitter", "????????????");
        keywordMap.put("unique", "?????????");
        keywordMap.put("strong", "?????? ??????");
        keywordMap.put("middle", "?????? ??????");
        keywordMap.put("mild", "?????? ??????");
        keywordMap.put("white", "?????????");
        keywordMap.put("red", "??????");
        keywordMap.put("astringent", "??????");
        keywordMap.put("fruit", "?????????");
        keywordMap.put("nonAlcoholic", "?????????");
        keywordMap.put("highAcidity", "?????? ??????");
        keywordMap.put("middleAcidity", "?????? ??????");
        keywordMap.put("lowAcidity", "?????? ??????");

        return keywordMap;
    }

    private boolean likeCurrentAlcohol(Alcohol alcohol, Long memberId) {
        if (isNull(memberId)) {
            return false;
        }
        return preferredAlcoholRepository.existsByMemberAndAlcohol(getMemberOrElseThrow(memberId), alcohol);
    }

    private Member getMemberOrElseThrow(long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(NOT_EXIST_MEMBER::getException);
    }

    public Long likeAlcohol(long alcoholId, long memberId) {
        validateMaxPreferredAlcoholCount(memberId);

        Alcohol alcohol = getAlcoholOrElseThrow(alcoholId);
        alcohol.plusLikeCount();

        return preferredAlcoholRepository
                .save(new PreferredAlcohol(getMemberOrElseThrow(memberId), alcohol))
                .getId();
    }

    private void validateMaxPreferredAlcoholCount(long memberId) {
        if (preferredAlcoholRepository.countByMemberId(memberId) >= 12) {
            throw MAX_PREFERRED_ALCOHOL_COUNT.getException();
        }
    }

    public void dislikeAlcohol(long alcoholId, long memberId) {
        Alcohol alcohol = getAlcoholOrElseThrow(alcoholId);
        Member member = getMemberOrElseThrow(memberId);

        if (preferredAlcoholRepository.existsByMemberAndAlcohol(member, alcohol)) {
            preferredAlcoholRepository.removeByMemberAndAlcohol(member, alcohol);
            alcohol.minusLikeCount();
        }
    }

    public List<AlcoholImageDto> getAlcoholImages(Long memberId) {
        return convertToAlcoholImage(preferredAlcoholRepository.getAlcohols(memberId, 12L));
    }

    private List<AlcoholImageDto> convertToAlcoholImage(List<Alcohol> alcohols) {
        List<AlcoholImageDto> alcoholImages = new ArrayList<>();
        for (Alcohol alcohol : alcohols) {
            alcoholImages.add(new AlcoholImageDto(alcohol.getId(),
                    fileManager.getAlcoholImagePath(alcohol.getType(), alcohol.getFileName())));
        }

        return alcoholImages;
    }

    public Page<AlcoholDto> findAlcoholPage(Pageable pageable, AlcoholSearchConditionDto condition) {
        return alcoholRepository.findAllByType(pageable, condition)
                .map(alcohol -> new AlcoholDto(alcohol,
                        getAlcoholImagePath(alcohol),
                        getAlcoholKeywords(alcohol),
                        vendorRepository.getAlcoholVendors(alcohol.getId()),
                        preferredAlcoholRepository.getLikeCount(alcohol.getId())));
    }

    private List<String> getAlcoholKeywords(Alcohol alcohol) {
        List<String> alcoholKeywords = new ArrayList<>();
        for (String keyword : alcoholKeywordRepository.getAlcoholKeywords(alcohol.getId())) {
            alcoholKeywords.add(createKeywordMap().get(keyword));
        }

        return alcoholKeywords;
    }

    private String getAlcoholImagePath(Alcohol alcohol) {
        return fileManager.getAlcoholImagePath(alcohol.getType(), alcohol.getFileName());
    }

    public List<AlcoholImageDto> getMonthAlcohols() { //????????? ??????
        return convertToAlcoholImage(preferredAlcoholRepository.getMonthAlcohols());
    }

    public List<AlcoholImageDto> getYourAlcohols(Long loginMember) {
        return convertToAlcoholImage(preferredAlcoholRepository.getPreferredAlcoholByKeyword(loginMember));
    }

    public List<BestLikeDto> getBestLike(AlcoholType alcoholType, int count) {
        List<BestLikeDto> alcoholTypeDetailsList = new ArrayList<>();

        List<Long> preferList = preferredAlcoholRepository.getAlcoholByType(alcoholType, count);

        for (Long alcoholId : preferList) {
            Alcohol alcohol = alcoholRepository.findById(alcoholId).orElse(null);
            String alcoholImagePath = fileManager.getAlcoholImagePath(alcoholType, alcohol.getFileName());

            List<String> alcoholKeywords = getAlcoholKeywords(alcoholId);

            alcoholTypeDetailsList.add(BestLikeDto.of(alcohol, alcoholImagePath, alcoholKeywords,
                    preferredAlcoholRepository.getLikeCount(alcoholId)));
        }

        return alcoholTypeDetailsList;
    }

    public List<AlcoholImageDto> getRelatedAlcohols(long alcoholId) {
        final int RELATED_ALCOHOLS_LIMIT = 12;
        return alcoholRepository.findRelatedAlcohols(alcoholId, RELATED_ALCOHOLS_LIMIT).stream()
                .map(alcohol -> new AlcoholImageDto(alcohol.getId(), getAlcoholImagePath(alcohol)))
                .collect(Collectors.toList());
    }
}

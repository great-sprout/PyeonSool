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
                getAlcoholKeywordNames(alcoholId),
                vendorRepository.getAlcoholVendorNames(alcoholId),
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

    private List<String> getAlcoholKeywordNames(Long alcoholId) {
        List<String> alcoholKeywordNames = new ArrayList<>();
        Map<String, String> keywordNameMap = createKeywordNameMap();
        for (String engKeywordName : alcoholKeywordRepository.getAlcoholKeywordNames(alcoholId)) {
            alcoholKeywordNames.add(keywordNameMap.get(engKeywordName));
        }
        return alcoholKeywordNames;
    }

    public static Map<String, String> createKeywordNameMap() {
        Map<String, String> keywordMap = new HashMap<>();
        keywordMap.put("sweet", "달콤함");
        keywordMap.put("clear", "깔끔함");
        keywordMap.put("cool", "청량함");
        keywordMap.put("heavy", "무거움");
        keywordMap.put("light", "가벼움");
        keywordMap.put("nutty", "고소함");
        keywordMap.put("fresh", "상큼함");
        keywordMap.put("flower", "꽃향");
        keywordMap.put("bitter", "쌉싸름함");
        keywordMap.put("unique", "독특함");
        keywordMap.put("strong", "도수 독함");
        keywordMap.put("middle", "도수 중간");
        keywordMap.put("mild", "도수 순함");
        keywordMap.put("white", "화이트");
        keywordMap.put("red", "레드");
        keywordMap.put("astringent", "떫음");
        keywordMap.put("fruit", "과일향");
        keywordMap.put("nonAlcoholic", "무알콜");
        keywordMap.put("highAcidity", "높은 산도");
        keywordMap.put("middleAcidity", "중간 산도");
        keywordMap.put("lowAcidity", "낮은 산도");

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
                        getAlcoholKeywordNames(alcohol),
                        vendorRepository.getAlcoholVendorNames(alcohol.getId()),
                        preferredAlcoholRepository.getLikeCount(alcohol.getId())));
    }

    private List<String> getAlcoholKeywordNames(Alcohol alcohol) {
        List<String> alcoholKeywords = new ArrayList<>();
        for (String keyword : alcoholKeywordRepository.getAlcoholKeywordNames(alcohol.getId())) {
            alcoholKeywords.add(createKeywordNameMap().get(keyword));
        }

        return alcoholKeywords;
    }

    private String getAlcoholImagePath(Alcohol alcohol) {
        return fileManager.getAlcoholImagePath(alcohol.getType(), alcohol.getFileName());
    }

    public List<AlcoholImageDto> getMonthAlcohols() { //이달의 추천
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

            List<String> alcoholKeywords = getAlcoholKeywordNames(alcoholId);

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

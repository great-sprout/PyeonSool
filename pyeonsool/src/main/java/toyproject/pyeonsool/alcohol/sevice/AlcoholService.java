package toyproject.pyeonsool.alcohol.sevice;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import toyproject.pyeonsool.common.FileManager;
import toyproject.pyeonsool.alcohol.repository.AlcoholRepository;
import toyproject.pyeonsool.alcohol.repository.AlcoholSearchConditionDto;
import toyproject.pyeonsool.alcoholkeyword.repository.AlcoholKeywordRepository;
import toyproject.pyeonsool.domain.*;
import toyproject.pyeonsool.member.repository.MemberRepository;
import toyproject.pyeonsool.preferredalcohol.repository.PreferredAlcoholRepository;
import toyproject.pyeonsool.review.repository.ReviewRepository;
import toyproject.pyeonsool.vendor.repository.VendorRepository;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.*;

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


    public AlcoholDetailsDto getAlcoholDetails(Long alcoholId, Long memberId) {
        Alcohol alcohol = alcoholRepository.findById(alcoholId).orElse(null);
        String alcoholImagePath = fileManager.getAlcoholImagePath(alcohol.getType(), alcohol.getFileName());
        List<String> alcoholKeywords = new ArrayList<>();
        Map<String, String> keywordMap = createKeywordMap();
        for (String keyword : alcoholKeywordRepository.getAlcoholKeywords(alcoholId)) {
            alcoholKeywords.add(keywordMap.get(keyword));
        }

        List<String> alcoholVendors = vendorRepository.getAlcoholVendors(alcoholId);
        String grade = getFormattedTotalGrade(reviewRepository.getReviewGrades(alcoholId));

        if (isNull(memberId)) {
            return AlcoholDetailsDto.of(alcohol, alcoholImagePath, grade, alcoholKeywords, alcoholVendors);
        }

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 회원입니다."));
        // TODO 술, 회원 예외처리 필요

        return AlcoholDetailsDto.of(alcohol, alcoholImagePath, grade, alcoholKeywords, alcoholVendors,
                preferredAlcoholRepository.existsByMemberAndAlcohol(member, alcohol));

    }

    private String getFormattedTotalGrade(List<Byte> reviewGrades) {
        if (reviewGrades.size() != 0) {
            double ratingAvg = (double) reviewGrades.stream().mapToLong(rating -> rating).sum() / reviewGrades.size();
            return String.format("%.1f", ratingAvg);
        }

        return "-";
    }

    public static Map<String, String> createKeywordMap() {
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

    public Long likeAlcohol(Long alcoholId, Long memberId) {
        Alcohol alcohol = alcoholRepository.findById(alcoholId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 술입니다."));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 회원입니다."));
        // TODO 술, 회원 예외처리 필요



        PreferredAlcohol preferredAlcohol = new PreferredAlcohol(member, alcohol);
        preferredAlcoholRepository.save(preferredAlcohol);
        alcohol.plusLikeCount();
        System.out.println("plus : " + alcohol.getName() + "cnt : " +alcohol.getLikeCount());


        return preferredAlcohol.getId();
    }

    public void dislikeAlcohol(Long alcoholId, Long memberId) {
        Alcohol alcohol = alcoholRepository.findById(alcoholId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 술입니다."));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 회원입니다."));
        // TODO 술, 회원 예외처리 필요

        if (preferredAlcoholRepository.existsByMemberAndAlcohol(member, alcohol)) {
            preferredAlcoholRepository.removeByMemberAndAlcohol(member, alcohol);
            alcohol.minusLikeCount();
            System.out.println("minus : " + alcohol.getName()+"cnt : "+alcohol.getLikeCount());
        }
    }

    //내 Like 리스트 부분 서비스
    public List<AlcoholImageDto> getAlcoholImages(Long memberId) {
        return convertToAlcoholImage(preferredAlcoholRepository.getAlcohols(memberId, 12L));
    }

    //내 Like 리스트 부분 서비스
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

    public List<AlcoholImageDto> getMonthAlcohols() { //이달의 추천
        return convertToAlcoholImage(preferredAlcoholRepository.getMonthAlcohols());
    }

    //나의 키워드가 포함된 추천 알콜 조회
    public List<AlcoholImageDto> getYourAlcohols(Long loginMember) {
        return convertToAlcoholImage(preferredAlcoholRepository.getPreferredAlcoholByKeyword(loginMember));
    }

    public List<BestLikeDto> getBestLike(AlcoholType alcoholType, int count) { //베스트 Like
        List<BestLikeDto> alcoholTypeDetailsList = new ArrayList<>(); //해당 술 DTO List

        List<Long> preferList = preferredAlcoholRepository.getAlcoholByType(alcoholType, count); //alcohol_id List
        //각각의 alcoholType에 맞는 DTO를 찾아 List에 담는다
        for (Long alcoholId : preferList) {
            Alcohol alcohol = alcoholRepository.findById(alcoholId).orElse(null);
            String alcoholImagePath = fileManager.getAlcoholImagePath(alcoholType, alcohol.getFileName());

            List<String> alcoholKeywords = new ArrayList<>(); //keyword List
            Map<String, String> keywordMap = createKeywordMap(); //keyword Map(key, Value)
            for (String keyword : alcoholKeywordRepository.getAlcoholKeywords(alcoholId)) {
                alcoholKeywords.add(keywordMap.get(keyword)); //영어로 된 key를 통해 value를 가져온다
            }

            alcoholTypeDetailsList.add(BestLikeDto.of(alcohol, alcoholImagePath, alcoholKeywords,
                    preferredAlcoholRepository.getLikeCount(alcoholId)));
        }

        return alcoholTypeDetailsList;
    }

    public List<AlcoholImageDto> getRelatedAlcohols(Long alcoholId) {
        int RELATED_ALCOHOLS_LIMIT = 12;
        return alcoholRepository.findRelatedAlcohols(alcoholId, RELATED_ALCOHOLS_LIMIT).stream()
                .map(alcohol -> new AlcoholImageDto(alcohol.getId(), getImagePath(alcohol)))
                .collect(Collectors.toList());
    }

    private String getImagePath(Alcohol alcohol) {
        return fileManager.getAlcoholImagePath(alcohol.getType(), alcohol.getFileName());
    }
}
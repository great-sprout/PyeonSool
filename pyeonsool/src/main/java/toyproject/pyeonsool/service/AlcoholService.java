package toyproject.pyeonsool.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import toyproject.pyeonsool.FileManager;
import toyproject.pyeonsool.domain.Alcohol;
import toyproject.pyeonsool.domain.AlcoholType;
import toyproject.pyeonsool.domain.Member;
import toyproject.pyeonsool.domain.PreferredAlcohol;
import toyproject.pyeonsool.repository.*;

import javax.transaction.Transactional;
import java.util.*;

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
        Alcohol alcohol = alcoholRepository.findById(alcoholId).orElse(null); //Alcohol 엔티티
        String alcoholImagePath = fileManager.getAlcoholImagePath(alcohol.getType(), alcohol.getFileName()); //imagePath
        List<String> alcoholKeywords = new ArrayList<>(); //keyword List(한글)
        Map<String, String> keywordMap = createKeywordMap(); //keyword Map(영어)
        for (String keyword : alcoholKeywordRepository.getAlcoholKeywords(alcoholId)) {
            alcoholKeywords.add(keywordMap.get(keyword)); //영어로 된 key를 통해 value를 가져온다
        }

        List<String> alcoholVendors = vendorRepository.getAlcoholVendors(alcoholId); //제조업체
        String grade = getFormattedTotalGrade(reviewRepository.getReviewGrades(alcoholId)); //별점

        if (isNull(memberId)) { //비로그인 상태이면
            return AlcoholDetailsDto.of(alcohol, alcoholImagePath, grade, alcoholKeywords, alcoholVendors);
        }

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 회원입니다."));
        // TODO 술, 회원 예외처리 필요

        return AlcoholDetailsDto.of(alcohol, alcoholImagePath, grade, alcoholKeywords, alcoholVendors,
                preferredAlcoholRepository.existsByMemberAndAlcohol(member, alcohol));

    }

    private String getFormattedTotalGrade(List<Byte> reviewGrades) {
        if (reviewGrades.size() != 0) { //리뷰가 있다면
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

    public Page<AlcoholDto> findAlcoholPage(AlcoholType alcoholType, Pageable pageable) {
        return alcoholRepository.findAllByType(alcoholType, pageable)
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

    public List<MainPageDto> getMonthAlcohols() {
        //이달의 추천
        List<Long> preferAlcohols = preferredAlcoholRepository.getAlcoholIds(); //alcohol_id List
        List<MainPageDto> alcoholDetailsList = new ArrayList<>(); //해당 술 DTO List
        //각각의 alcohol_id에 맞는 DTO를 찾아 List에 담는다
        for (Long preferAlcohol : preferAlcohols) {
            Alcohol alcohol = alcoholRepository.findById(preferAlcohol).orElse(null);
            String alcoholImagePath = fileManager.getAlcoholImagePath(alcohol.getType(), alcohol.getFileName());

            List<String> alcoholKeywords = new ArrayList<>(); //keyword List(한글)
            Map<String, String> keywordMap = createKeywordMap(); //keyword Map(key, Value)
            for (String keyword : alcoholKeywordRepository.getAlcoholKeywords(preferAlcohol)) {
                alcoholKeywords.add(keywordMap.get(keyword)); //영어로 된 key를 통해 value를 가져온다
            }

            alcoholDetailsList.add(MainPageDto.of(alcohol, alcoholImagePath, alcoholKeywords,
                    preferredAlcoholRepository.getLikeCount(preferAlcohol)));
        }
        return alcoholDetailsList;
    }

    public List<MainPageDto> getBestLike(AlcoholType alcoholType,int count) { //각 타입별 술 DTO 반환
        //베스트 Like
        List<MainPageDto> alcoholTypeDetailsList = new ArrayList<>(); //해당 술 DTO List

        List<Long> preferList= preferredAlcoholRepository.getAlcoholByType(alcoholType,count); //alcohol_id List

        //각각의 alcoholType에 맞는 DTO를 찾아 List에 담는다

        for (Long alcoholId : preferList) {
            Alcohol alcohol = alcoholRepository.findById(alcoholId).orElse(null);
            String alcoholImagePath = fileManager.getAlcoholImagePath(alcoholType, alcohol.getFileName());

            List<String> alcoholKeywords = new ArrayList<>(); //keyword List(한글)
            Map<String, String> keywordMap = createKeywordMap(); //keyword Map(key, Value)
            for (String keyword : alcoholKeywordRepository.getAlcoholKeywords(alcoholId)) {
                alcoholKeywords.add(keywordMap.get(keyword)); //영어로 된 key를 통해 value를 가져온다
            }

            alcoholTypeDetailsList.add(MainPageDto.of(alcohol, alcoholImagePath, alcoholKeywords,
                    preferredAlcoholRepository.getLikeCount(alcoholId)));
        }

        return alcoholTypeDetailsList;
    }
}

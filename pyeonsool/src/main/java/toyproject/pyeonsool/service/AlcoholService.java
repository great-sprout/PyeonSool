package toyproject.pyeonsool.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
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

    private final PreferredAlcoholCustomRepositoryImpl preferredAlcoholCustomRepositoryImpl;

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

    private Map<String, String> createKeywordMap() {
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
   
   public MyPageDto MyPage(String nickname){
        Member findMember = memberRepository.findByNickname(nickname);
        Long memberId = findMember.getId();
        List<Long> alcoholIds = preferredAlcoholCustomRepositoryImpl.getMyList(memberId);
        List<String> imagePaths = new ArrayList<>();

        for(int i = 0; i<alcoholIds.size(); i++){
            Optional<Alcohol> alcohol = alcoholRepository.findById(alcoholIds.get(i));
            String imagePath= fileManager.getAlcoholImagePath(alcohol.get().getType(),alcohol.get().getFileName());
            imagePaths.add(imagePath);
        }
        System.out.println("imagePaths = " + imagePaths);
        System.out.println("alcoholIds = " + alcoholIds);

        MyPageDto myLikeList = new MyPageDto(memberId,alcoholIds,imagePaths);
        return myLikeList;
    }

    public List<AlcoholTypeDto> findTypeAlcohol(AlcoholType byType) {
        List<Alcohol> alcoholsList = alcoholRepository.findAllByType(byType);//알콜 정보 리스트
        List<AlcoholTypeDto> result =new ArrayList<>();
        List<String> imagePathList = new ArrayList<>();//알콜 사진 리스트
        List<String> keywordList = new ArrayList<>();
        List<String> vendorList = new ArrayList<>();
        for(int i=0;i<alcoholsList.size();i++){
            System.out.println("list num = "+i);
            Alcohol tempAlcohol = alcoholsList.get(i);
            String alcoholImagePath = fileManager.getAlcoholImagePath(tempAlcohol.getType(), tempAlcohol.getFileName());
            List<String> alcoholKeywords = new ArrayList<>();//각 알콜의 편의점 리스트를 담아두는 곳
            Map<String, String> keywordMap = createKeywordMap();
            for (String keyword : alcoholKeywordRepository.getAlcoholKeywords(tempAlcohol.getId())) {
                alcoholKeywords.add(keywordMap.get(keyword));
            }
            List<String> alcoholVendors = vendorRepository.getAlcoholVendors(tempAlcohol.getId());
            AlcoholTypeDto tempDto = new AlcoholTypeDto(alcoholsList.get(i),
                    alcoholImagePath,
                    alcoholKeywords,
                    alcoholVendors);
            result.add(tempDto);
        }
        return result;
    }
}

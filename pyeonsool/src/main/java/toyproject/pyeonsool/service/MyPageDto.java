package toyproject.pyeonsool.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import toyproject.pyeonsool.domain.Member;
import toyproject.pyeonsool.domain.PreferredAlcohol;

import java.util.List;

@Data
public class MyPageDto {
    private Long memberId;
    private List<Long> alcoholIds;

    private List<String> imagePaths;


    //값만띄워서 값전달

    public MyPageDto(Long memberId, List<Long> alcoholIds, List<String> imagePaths) {
        this.memberId = memberId;
        this.alcoholIds = alcoholIds;
        this.imagePaths = imagePaths;

    }
}

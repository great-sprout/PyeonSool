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
    //마이페이지 dto이므로 이에 해당하는 멤버의 아이디가 들어가야한다.
    //멤버가 좋아요누른 목록도 있어야 하므로 좋아요누른 술들의 아이디와 이미지 저장경로 작성
    private Long memberId;
    private List<Long> alcoholIds;
    private List<String> imagePaths;

    public MyPageDto(Long memberId, List<Long> alcoholIds, List<String> imagePaths) {
        this.memberId = memberId;
        this.alcoholIds = alcoholIds;
        this.imagePaths = imagePaths;
    }
}

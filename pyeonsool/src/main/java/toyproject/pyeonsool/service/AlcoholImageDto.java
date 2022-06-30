package toyproject.pyeonsool.service;

import lombok.AllArgsConstructor;
import lombok.Data;

//마이페이지 내 Like리스트 부분 정보
@Data
@AllArgsConstructor
public class AlcoholImageDto {
    private Long alcoholId;
    private String imagePath;
}

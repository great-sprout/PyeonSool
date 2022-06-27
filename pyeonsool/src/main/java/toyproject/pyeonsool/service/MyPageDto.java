package toyproject.pyeonsool.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import toyproject.pyeonsool.domain.Member;
@Data
public class MyPageDto {
   private Long memberId;
   private Long alcohol;
   private Long preferredAlcoholId;
   private String imagePath;

   //값만띄워서 값전달

   public MyPageDto(Long memberId, Long alcohol, Long preferredAlcoholId, String imagePath) {
      this.memberId = memberId;
      this.alcohol = alcohol;
      this.preferredAlcoholId = preferredAlcoholId;
      this.imagePath = imagePath;
   }
}

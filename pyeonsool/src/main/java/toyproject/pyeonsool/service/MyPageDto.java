package toyproject.pyeonsool.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import toyproject.pyeonsool.domain.Member;
import toyproject.pyeonsool.domain.PreferredAlcohol;

@Data
public class MyPageDto {
   private Long memberId;
   private Long alcoholId;
   private Long preferredAlcoholId;
   private String imagePath;



   //값만띄워서 값전달

   public MyPageDto(Long memberId, Long alcoholId, Long preferredAlcoholId,String imagePath) {
      this.memberId = memberId;
      this.alcoholId = alcoholId;
      this.preferredAlcoholId = preferredAlcoholId;
      this.imagePath = imagePath;

   }

   public MyPageDto(PreferredAlcohol pa ,String imagePath) {
      return this(
              pa.getMember().getId(),
              pa.getAlcohol().getId(),
              pa.getId(),
              imagePath);
   }
}

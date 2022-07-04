package toyproject.pyeonsool.member.web;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class MemberSaveForm {
    @NotBlank(message = "닉네임을 입력하세요.")
    @Size(min = 1,max = 20,message = "1 ~ 20 자로 입력하세요.")
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-Z0-9]*$",message = "특수문자는 입력이 불가능 합니다.")
    private String nickname;

    @NotBlank(message = "아이디를 입력하세요.")
    @Size(min = 1,max = 20,message = "1 ~ 20 자로 입력하세요.")
    @Pattern(regexp = "^[a-zA-Z1-9]*$",message = "숫자와 영어로만 입력하세요.")
    private String userId;

    @NotBlank(message = "비밀번호를 입력하세요.")
    @Size(min = 4,max = 20,message = "4 ~ 20 자로 입력하세요.")
    private String password;

    @NotBlank(message = "전화번호를 입력하세요.")
    @Size(min = 11,max = 11,message = "11 자로 입력하세요.")
    @Pattern(regexp = "^[0-9]*$+",message = "숫자로만 입력하세요.")
    private String phoneNumber;

    @NotNull(message = "3가지 고르세요!")
    @Size(min=3,max = 3,message = "3가지만 고르세요!")
    private List<String> keywords;
}

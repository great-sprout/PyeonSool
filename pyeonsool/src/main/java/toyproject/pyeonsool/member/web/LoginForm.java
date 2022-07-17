package toyproject.pyeonsool.member.web;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginForm {
    @NotBlank(message = "아이디를 입력하세요.")
    private String userId;

    @NotBlank(message = "비밀번호를 입력하세요.")
    private String password;
}

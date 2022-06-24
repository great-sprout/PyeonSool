package toyproject.pyeonsool;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class LoginMember {
    private Long id;
    private String nickname;
}

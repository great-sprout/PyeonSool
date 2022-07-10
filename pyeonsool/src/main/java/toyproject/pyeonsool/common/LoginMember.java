package toyproject.pyeonsool.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class LoginMember {
    private long id;
    private String nickname;
}

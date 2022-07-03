package toyproject.pyeonsool.web;

import lombok.Data;

import java.util.List;

@Data
public class MemberSaveForm {
    private String nickname;
    private String userId;
    private String password;
    private String phoneNumber;
    private List<String> keywords;
}

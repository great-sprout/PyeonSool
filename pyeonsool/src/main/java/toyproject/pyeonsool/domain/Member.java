package toyproject.pyeonsool.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static toyproject.pyeonsool.common.exception.form.FormExceptionType.INCORRECT_PASSWORD;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "member_nickname_uk", columnNames = {"nickname"}),
        @UniqueConstraint(name = "member_userId_uk", columnNames = {"userId"}),
        @UniqueConstraint(name = "member_phoneNumber_uk", columnNames = {"phoneNumber"})})
public class Member {

    @Id
    @GeneratedValue
    @Column(name ="member_id")
    private Long id;

    @Column(length = 40)
    private String nickname;

    @Column(length = 20)
    private String userId;

    @Column(length = 20)
    private String password;

    @Column(length = 14)
    private String phoneNumber;

    public Member(String nickname, String userId, String password, String phoneNumber) {
        this.nickname = nickname;
        this.userId = userId;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public void validatePassword(String password) {
        if (!this.getPassword().equals(password)) {
            throw INCORRECT_PASSWORD.getException();
        }
    }
}

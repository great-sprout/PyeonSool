package toyproject.pyeonsool.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
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
}

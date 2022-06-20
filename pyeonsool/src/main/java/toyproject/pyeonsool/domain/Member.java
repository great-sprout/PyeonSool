package toyproject.pyeonsool.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = {@UniqueConstraint(name = "member_nickname_uk", columnNames = {"nickname"}),
        @UniqueConstraint(name = "member_userId_uk", columnNames = {"userID"}),
        @UniqueConstraint(name = "member_phoneNumber_uk", columnNames = {"phoneNumber"})})
public class Member {

    @Id
    @GeneratedValue
    @Column(name ="member_id")
    private Long id;
    private String nickame;
    private String userId;
    private String password;
    private String phoneNumber;

    @OneToMany(mappedBy = "member")
    private List<PreferredAlcohol> prefereedAlcohol = new ArrayList<>();
}

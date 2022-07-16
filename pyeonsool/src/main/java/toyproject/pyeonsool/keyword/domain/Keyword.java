package toyproject.pyeonsool.keyword.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "keyword_name_uk", columnNames = {"name"})})
public class Keyword {

    @Id
    @GeneratedValue
    @Column(name = "keyword_id")
    private Long id;

    @Column(length = 16)
    private String name;

    public Keyword(String name) {
        this.name = name;
    }
}

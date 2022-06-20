package toyproject.pyeonsool.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "keyword_name_uk", columnNames = {"name"})})
public class Keyword {

    @Id
    @GeneratedValue
    @Column(name="keyword_id")
    private Long id;
    private String name;
}

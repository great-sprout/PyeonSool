package toyproject.pyeonsool.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Keyword {

    @Id
    @GeneratedValue
    @Column(name="keyword_id")
    private Long id;
    private String name;

    //알콜 키워드
    //마이 키워드
    @OneToMany(mappedBy = "keyword")
    private List<AlcoholKeyword> alcoholKeywords = new ArrayList<>();
    private List<MyKeyword> myKeywords = new ArrayList<>();





}

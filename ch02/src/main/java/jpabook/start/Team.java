package jpabook.start;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Team {
    @Id
    @Column(name = "TEAM_ID")
    private Integer id;

    private String name;

    /* mappedBy를 사용해 두 객체간의 연관관계의 주인을 설정
    * 왜래키가 있는 곳을 연관관계의 주인으로 설정
    * 여기선 MEMBER가 TEAM의 키를 갖고 있으므로 아래와 같이 설
    *
     */
    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }


}

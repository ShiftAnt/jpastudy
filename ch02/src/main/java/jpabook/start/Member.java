package jpabook.start;

import javax.persistence.*;
import java.util.Date;

@Entity
/* Access: JPA가 엔티티 데이터에 접근하는 방식 지정
* FIELD: 필드에 직접 접근, private여도 접근 가능
* PROPERTY: 접근자 Getter 사용하는 프로퍼티 접근
* */
@Access(AccessType.FIELD)
@Table(name="MEMBER",
uniqueConstraints = {   // 유니크 제약조건
    @UniqueConstraint(
            name = "NAME_AGE_UNIQUE",   // 제약조건 이름
            columnNames = {"NAME", "AGE"}   // 제약 조건 대상
    )
})
public class Member {

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "NAME",
            nullable = false, length = 10)  // 제약조건 추가
    private String username;
    private Integer age;

    /* STRING(권장): enum의 이름 자체로 매핑함
    * ORDINAL(default): 각 enum의 순서로 매핑함 0, 1, 2 ... */
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    /* 날짜 타입(java.util.Date, Calendar)을 매핑할 때 사용
    * DATE: 년월일만 있는 DB의 date 타입과 매핑
    * TIME: 시분초만 있는 DB의 time 타입과 매핑
    * TIMESTAMP: 년월일시분초가 있는 DB의 timestamp(= datetime) 타입과 매핑
    *  */
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    /* 필드 타입이 문자면 CLOB, 나머지는 BLOB로 매핑 */
    @Lob
    private String description;

    /* Transient: 이 필드는 매핑하지 않음
    * DB에 저장하지 않고 조회하지 않음, 임시로 값을 보관하고 싶을 때 사용
    *  */
    @Transient
    private Integer temp;


    /*
    * 일대다, 다대일 관계에서는 항상 다 쪽이 외래키를 가짐 @ManyToOne은 항상 연관관계의 주인이 되므로
    mappedBy 속성을 사용할 우 없고 존재하지도 않는다.
     */
    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    public void setTeam(Team team) {
        this.team = team;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}

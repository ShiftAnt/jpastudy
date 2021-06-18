package jpabook.start;

import javax.persistence.*;
import java.util.Date;

@Entity
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

    @Enumerated(EnumType.STRING)
    private


    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @Lob // DB CLOB, BLOB 타입 매핑
    private String description;

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

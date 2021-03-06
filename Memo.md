# JPA

### 1.2 패러다임의 불일치

객체와 관계형 데이터베이스는 지향하는 목적이 서로 다르므로 둘의 기능과 표현 방법도 다르다. 이것을 객체와 관계형 데이터베이스의 패러다임 불일치 문제라 함.





### 3.3 엔티티의 생명주기

* 비영속(new / transient): 영속성 컨텍스트와 전혀 관계가 없는 상태

  엔티티 객제를 생성만 한 상태

* 영속(managed): 영속성 컨텍스트에 저장된 상태

  엔티티 매너저를 통해 엔티티를 영속성 컨텍스트에 저장

  `em.persist(member);`

* 준영속(detached): 영속성 컨텍스트에 저장되었다가 분리된 상태

  영속성 컨텍스트가 관리하던 영속 상태의 엔티티를 영속성 컨텍스트가 관리하지 않으면 준영속 상태가 됨

  `em.detach();`

* 삭제(removed): 삭제된 상태

  엔티티를 영속성 컨텍스트와 데이터베이스에서 삭제

  `em.remove(member);`



### 3.6

3.6.4 준영속 상태의 특징

* 거의 비영속 상태에 가까움
* 식별자 값을 가지고 있음
  * 비영속 상태는 식별자 값이 없을 수도 있지만, 준영속 상태는 이미 한 번 영속 상태였으므로 반드시 식별자 값을 가지고 있음
* 지연 로딩을 할 수 있음



3.6.5 병합 merge()

* 병합은 준영속 엔티티 뿐만 아니라 비영속 엔티티도 영속 상태로 만들 수 있음
  * 단, 대상 객체가 영속 엔티티가 되는 것이 아닌 영속 엔티티가 된 객체를 반환한다.



## 4. 엔티티 매핑

### 4.1 @Entity

* JPA를 사용해 테이블과 매핑할 클래스는 무조건 붙여야 함
* 기본 생성자는 필수



### 4.2 @Table

* 엔티티와 매핑할 테이블 지정



### 4.6 기본 키 매핑

1. 직접 할당 전략

   `@Id`로 매핑

   `.persist()`로 엔티티를 저장하기 전 애플리케이션에서 직접 기본키를 할당

   > `.setId("id1")`
   >
   > 식별자 값이 없으면 에러

2. IDENTITY 전략

   기본 키 생성을 DB에 위임

   MySQL, PostgreSQL, SQL Server, DB2에서 사용

   MySQL의 auto_increment와 같이 기본 키 생성을 DB에 위임함

   `GeneratedValue(strategy = GenerationType.IDENTITY)`

3. SEQUENCE 전략

   DB 시퀀스는 유일한 값을 순서대로 생성하는 특별한 DB 오브젝트

   SEQUENCE 전략은 이 시퀀스를 사용해 기본 키 생성

4. Table 전략

   키 생성 전용 테이블을 하나 만들고 여기에 이름과 값으로 사용할 컬럼을 만들어 DB 시퀀스를 흉내내는 전략

5. AUTO 전략

   선택한 DB 방언에 따라 IDENTITY, SEQUENCE, TABLE 전략 중 하나를 자동으로 선택

   `@GeneratedValue.strategy`의 기본 값 `@GeneratedValue`만 쓰는 것과 동일함



## 5. 연관관계 매핑 기초

### 5.4 연관관계 주인

* 연관관계 주인은 mappedBy를 사용하지 않음
* 주인이 아니면 mappedBy 속성을 사용해 속성의 값으로 연관관계의 주인을 지정해야함

* 참고
  * DB 테이블의 다대일, 일대다 관계에서는 항상 다 쪽이 외래키를 가짐
  * 다 쪽인 `@ManyToOne` 은 항상 연관관계의 주인이 되므로 mappedBy를 설정할 수 없음
  * 따라서 ManyToOne에는 mappedBy 속성이 없음



### 5.5 양방향 연관관계 저장

* 연관관계의 주인인이 외래 키를 관리하므로 주인이 아닌 방향에서 값을 설정하더라도 반영되지 않음

  ```java
  class Member {
    /* 생략 */
    @ManyToOne
    private Team team;
  }
  class Team {
    /* 생략 */
    @OneToMany(mappedBy = "team")
    List<Member> members = new ArrayList<>();
  }
  ...
    
  team.getMembers().add(member);	// 연관관계의 주인이 아니므로 반영되지 않음
  ```

  

package jpabook.start;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    private static EntityManagerFactory emf
            = Persistence.createEntityManagerFactory("jpabook");
    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            System.out.println("gi");
            tx.begin();
            logic(em);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        emf.close();
    }
    private static void logic(EntityManager em) {
        String id = "id1";
        Member member = new Member();
        member.setId(id);
        member.setUsername("지한");
        member.setAge(2);

        //등록
        em.persist(member);

        //수정
        member.setAge(20);

        //한 건 조회
        Member findMember = em.find(Member.class, id);
        System.out.println("findMember=" + findMember.getUsername() + ", age=" + findMember.getAge());

        //목록 조회
        List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();

        System.out.println("members.size=" + members.size());

        em.remove(member);
    }

    /* 영속성 해제 */
    private void closeEntityManager(EntityManager em, EntityTransaction transaction) {
        transaction.begin();

        Member memberA = em.find(Member.class, "memberA");
        Member memberB = em.find(Member.class, "memberB");

        transaction.commit();

        /* 영속성 컨텍스트가 종료되어 더는 memberA, memberB가 관리되지 않음
         * 주로 영속성 컨텍스트가 종료되면서 준영속 상태가 됨
         * 개발자가 직접 detach()로 준영속 상태로 만드는 경우는 드뭄
         */
        em.close();
    }

    /* 준영속 상태의 엔티티를 영속 상태로 변환 */
    private void restoreEntityDetachToPersist() {

        /* 영속성 컨텍스트를 .close() 하고 반환했으므로 member는 준영속 상태 */
        Member member = createMember("member1", "회원1");

        member.setUsername("회원명변경"); //현재 member는 준영속 상태
    }

    private Member createMember(String id, String username) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Member member = new Member();
        member.setId(id);
        member.setUsername(username);

        em.persist(member);
        transaction.commit();

        em.close(); // 영속성 컨텍스트가 종료되고 member 엔티티는 준영속 상태가 됨

        return member;
    }

    private void mergeMember(Member member) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        transaction.begin();

        /* member 엔티티는 그대로 준영속 상태이고 새로운 영속 상태의 객체를 반환함*/
        Member mergeMember = em.merge(member);
        transaction.commit();

        em.close();
    }

}

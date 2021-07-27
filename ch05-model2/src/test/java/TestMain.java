import jpabook.jpa.EntityManagerFactorySingleton;
import jpabook.model.entity.Member;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class TestMain {
    private EntityManagerFactory emf = EntityManagerFactorySingleton.getInstance();
    @Test
    public void test() {
        EntityManager em = emf.createEntityManager();

        Member member = new Member();
        member.setId(1L);
        member.setName("woong");
    }
}

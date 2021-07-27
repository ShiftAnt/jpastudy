package jpabook.jpa;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerFactorySingleton {
    private EntityManagerFactorySingleton() {}

    private static class SingletonHelper {
        private static final EntityManagerFactory INSSTANCE
                = Persistence.createEntityManagerFactory("jpabook");
    }

    public static EntityManagerFactory getInstance() {
        return SingletonHelper.INSSTANCE;
    }
}

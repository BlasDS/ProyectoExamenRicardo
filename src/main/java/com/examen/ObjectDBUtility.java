package com.examen;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ObjectDBUtility {
    private static EntityManager entityManager;
    private ObjectDBUtility(){}
    public static EntityManager getEntityManager() {
        if (entityManager == null) {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("objectdb:productos.odb");
            entityManager = entityManagerFactory.createEntityManager();
        }
        return entityManager;
    }
}

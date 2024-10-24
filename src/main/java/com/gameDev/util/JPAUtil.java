package com.gameDev.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
    private static EntityManagerFactory entityManagerFactory;

    private JPAUtil() {
    }

    static {
        try {
            String persistentUnitName = System.getProperty("puName", "GameDev");
            entityManagerFactory = Persistence.createEntityManagerFactory(persistentUnitName);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ExceptionInInitializerError("EntityManagerFactory initialization failed.");
        }
    }

    public static EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    public static void close() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }



}

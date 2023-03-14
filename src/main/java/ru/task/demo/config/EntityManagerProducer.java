package ru.task.demo.config;

import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Singleton
public class EntityManagerProducer{

    @PersistenceContext
    private EntityManager entityManager;

    @Produces
    public EntityManager entityManager(){
        return entityManager;
    }
}
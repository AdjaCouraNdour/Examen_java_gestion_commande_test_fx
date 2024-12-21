package com.ism.core.Repository;

import java.util.*;

import com.ism.data.entities.Identifiable;

import jakarta.persistence.*;

public class RepositoryJPA<T extends Identifiable> implements Repository<T> {
    
    protected EntityManager em;
    protected EntityManagerFactory emf;
    protected Class<T> type;
    protected String tableName;

    public RepositoryJPA(Class<T> type) {
        this.type = type;
        if (em == null) {
            this.emf = Persistence.createEntityManagerFactory("POSTGRESQLDETTES");
            this.em = emf.createEntityManager(); 
        } else {
            this.em = em; 
        }
    }

    @Override
    public boolean insert(T object) {
        boolean success = false;
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(object); 
            transaction.commit();
            success = true;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return success;
    }

    @Override
    public List<T> selectAll() {
        String entityName = type.getSimpleName();  
        return this.em.createQuery("SELECT u FROM " + entityName + " u", type).getResultList();
    }


    @Override
    public T selectById(int id) {
        T entity = null;
        try {
            entity = em.find(type, id);  
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entity; 
    }

    
    public void close() {
        if (em != null && em.isOpen()) {
            em.close();
        }
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }

    @Override
    public void remove(T object) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            T mergedObject = em.merge(object);  
            em.remove(mergedObject);  
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public boolean update(T object) {
        boolean success = false;
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.merge(object); 
            transaction.commit();
            success = true;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return success;
    }

}

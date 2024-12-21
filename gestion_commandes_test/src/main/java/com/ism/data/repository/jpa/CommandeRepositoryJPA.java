package com.ism.data.repository.jpa;

import java.util.List;

import com.ism.core.Repository.RepositoryJPA;
import com.ism.data.entities.Commande;
import com.ism.data.enums.TypeCommande;
import com.ism.data.repository.interfaces.CommandeRepositoryI;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

public class CommandeRepositoryJPA extends RepositoryJPA<Commande> implements CommandeRepositoryI {

    public CommandeRepositoryJPA(Class<Commande> type) {
        super( type);
    }

    @Override
    public Commande selectById(int id) {
        Commande commande = null;
        try {
            commande = em.find(Commande.class, id);
        } catch (Exception e) {
            System.out.println("Erreur lors de la recherche de la commande par ID : " + e.getMessage());
        }
        return commande;
    }

    @Override
    public List<Commande> selectByType(TypeCommande type) {
        List<Commande> commandes = null;
        try {
            TypedQuery<Commande> query = em.createQuery(
                "SELECT d FROM Commande d WHERE d.typeCommande= :type", Commande.class);
            query.setParameter("type", type);
            commandes = query.getResultList();
        } catch (Exception e) {
            System.out.println("Erreur lors de la recherche de commandes par type : " + e.getMessage());
        }
        return commandes;
    }

    @Override
    public Commande selectBy(TypeCommande type) {
        Commande commande = null;
        try {
            TypedQuery<Commande> query = em.createQuery(
                "SELECT d FROM Commande d WHERE d.typeCommande = :type", Commande.class);
            query.setParameter("type", type);
            commande = query.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Aucune commande trouvée avec le type : " + type);
        } catch (Exception e) {
            System.out.println("Erreur lors de la recherche de la commande par type : " + e.getMessage());
        }
        return commande;
    }


    @Override
    public boolean update(Commande commande) {
        try {
            if (commande != null) {
                Commande existingCommande = em.find(Commande.class, commande.getId());
                if (existingCommande != null) {
                    em.getTransaction().begin();  
                    em.merge(commande);               
                    em.getTransaction().commit();  
                    return true;
                } else {
                    System.out.println("Aucune commande trouvée avec l'ID : " + commande.getId());
                    return false;
                }
            }
        } catch (Exception e) {
            em.getTransaction().rollback();  
            System.out.println("Erreur lors de la mise à jour de la commande : " + e.getMessage());
            return false;
        }
        return false;
    }

}

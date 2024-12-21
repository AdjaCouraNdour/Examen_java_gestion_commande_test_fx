package com.ism.data.repository.jpa;

import com.ism.core.Factory.FactoryRepositoryJPA;
import com.ism.core.Repository.RepositoryJPA;
import com.ism.data.entities.Client;
import com.ism.data.repository.interfaces.ClientRepositoryI;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

public class ClientRepositoryJPA extends RepositoryJPA<Client> implements ClientRepositoryI {

    FactoryRepositoryJPA factory = new FactoryRepositoryJPA();

    public ClientRepositoryJPA( Class<Client> type) {
        super(type);
    }

    // @Override
    // public boolean insert(Client object) {
    //     try {
    //         if (object.getUser() != null && factory.getInstanceRepoUser().selectById(object.getUser().getId()) == null) {
    //             em.persist(object.getUser());
    //             em.persist(object);
    //         }else{
    //             em.merge(object);
    //         }
    //         return true;
    //     } catch (Exception e) {
    //         e.printStackTrace(); 
    //         return false;
    //     }
    // }


    @Override
    public Client selectById(int id) {
        Client client = null;
        try {
            client = em.find(Client.class, id); 
            if (client == null) {
                System.out.println("Aucun client trouvé avec l'ID : " + id);
            }
        } catch (Exception e) {
            System.out.println("Erreur lors de la recherche du client par ID : " + e.getMessage());
        }
        return client;
    }

    @Override
    public Client selectByNumero(String numero) {
        Client client = null;
        try {
            TypedQuery<Client> query = em.createQuery(
                String.format("SELECT c FROM Client c WHERE c.telephone = :numero"),
                Client.class);
            query.setParameter("numero", numero); 
            client = query.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Aucun client trouvé avec le numéro : " + numero);
        } catch (Exception e) {
            System.out.println("Erreur lors de la recherche du client par numéro : " + e.getMessage());
        }
        return client;
    }

    @Override
    public Client selectConnectedClient(int userId) {
        try {
            TypedQuery<Client> query = em.createQuery(
                "SELECT c FROM Client c WHERE c.user.id = :userId", Client.class);
            query.setParameter("userId", userId);
            return query.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("Aucun client trouvé pour l'utilisateur ID : " + userId);
            return null;
        } catch (Exception e) {
            System.out.println("Erreur lors de la récupération du client pour l'utilisateur : " + e.getMessage());
            return null;
        }
    }
}

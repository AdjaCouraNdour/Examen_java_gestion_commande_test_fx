package com.ism.core.Factory;

import com.ism.data.entities.Produit;
import com.ism.data.entities.Client;
import com.ism.data.entities.Details;
import com.ism.data.entities.Commande;
import com.ism.data.entities.User;
import com.ism.data.repository.interfaces.ProduitRepositoryI;
import com.ism.data.repository.interfaces.ClientRepositoryI;
import com.ism.data.repository.interfaces.DetailsRepositoryI;
import com.ism.data.repository.interfaces.CommandeRepositoryI;
import com.ism.data.repository.interfaces.UserRepositoryI;
import com.ism.data.repository.jpa.ProduitRepositoryJPA;
import com.ism.data.repository.jpa.ClientRepositoryJPA;
import com.ism.data.repository.jpa.DetailsRepositoryJPA;
import com.ism.data.repository.jpa.CommandeRepositoryJPA;
import com.ism.data.repository.jpa.UserRepositoryJPA;

import jakarta.persistence.*;

public class FactoryRepositoryJPA {
    
    private static ClientRepositoryI clientRepository;
    private static UserRepositoryI userRepository;
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("POSTGRESQLDETTES");
    private static EntityManager em;
    private static ProduitRepositoryI produitRepository;
    private static CommandeRepositoryI commandeRepository;
    private static DetailsRepositoryI detailsRepository;



	public ClientRepositoryI getInstanceRepoClient(){
        if (clientRepository==null) {
             em = emf.createEntityManager();
            clientRepository = new ClientRepositoryJPA(Client.class);
        }
        return clientRepository;

    }

    public UserRepositoryI getInstanceRepoUser(){
        if (userRepository==null) {
            em=emf.createEntityManager();
            userRepository=new UserRepositoryJPA(User.class,clientRepository);
        }
        return userRepository;
    }   

    public ProduitRepositoryI getInstanceRepoProduit(){
        if (produitRepository==null) {
            em=emf.createEntityManager();
            produitRepository=new ProduitRepositoryJPA(Produit.class);
        }
        return produitRepository;
    }  

    public CommandeRepositoryI getInstanceRepoCommande(){
        if (commandeRepository==null) {
            em=emf.createEntityManager();
            commandeRepository=new CommandeRepositoryJPA(Commande.class);    
            }
        return commandeRepository;
    }   


    public DetailsRepositoryI getInstanceRepoDetails(){
        if (detailsRepository==null) {
            em=emf.createEntityManager();
            detailsRepository =new DetailsRepositoryJPA(Details.class);        
        }
        return detailsRepository;
    }  

}

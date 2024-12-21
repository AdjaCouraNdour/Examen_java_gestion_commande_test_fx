package com.ism.core.Factory;

import com.ism.data.services.list.ProduitService;
import com.ism.data.services.list.ClientService;
import com.ism.data.services.list.DetailsService;
import com.ism.data.services.list.CommandeService;
import com.ism.data.services.list.UserService;

public class FactoryService  {

    private static ClientService clientService ;
    private static UserService userService;
    private static ProduitService produitService;
    private static CommandeService commandeService;
    private static DetailsService detailsService ;
 
    FactoryRepositoryJPA factory = new FactoryRepositoryJPA();

    public ClientService getInstanceClientService(){
        if (clientService==null) {
            return clientService=new ClientService(factory.getInstanceRepoClient());
        }
        return clientService;
    }

    public UserService getInstanceUserService(){
        if (userService==null) {
            return userService=new UserService(factory.getInstanceRepoUser());
        }
        return userService;
    }

    public ProduitService getInstanceProduitService(){
        if (produitService==null) {
            return produitService=new ProduitService(factory.getInstanceRepoProduit());
        }
        return produitService;
    }
    public CommandeService getInstanceCommandeService(){
        if (commandeService==null) {
            return commandeService=new CommandeService(factory.getInstanceRepoCommande());
        }
        return commandeService;
    }
    public DetailsService getInstanceDetailsService(){
        if (detailsService==null) {
            return detailsService=new DetailsService(factory.getInstanceRepoDetails());
        }
        return detailsService;
    }
   
   
}

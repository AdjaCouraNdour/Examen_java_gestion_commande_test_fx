package com.ism.data.services.list;

import java.util.List;

import com.ism.data.entities.Commande;
import com.ism.data.enums.TypeCommande;
import com.ism.data.repository.interfaces.CommandeRepositoryI;
import com.ism.data.services.interfaces.DetteServiceI;

public class CommandeService implements DetteServiceI{

    CommandeRepositoryI repo;

    public CommandeService(CommandeRepositoryI repo) {
        this.repo = repo;
    }

    @Override
    public Commande getById(int id) {
        return
        repo.selectAll().stream()
        .filter(client -> client.getId()==id)
        .findFirst()
        .orElse(null);  
    }

    @Override
    public Commande getBy(TypeCommande etat) {
        return repo.selectBy(etat);
        // repo.selectAll().stream()
        // .filter(dette -> dette.getTypeDette().compareTo(etat)==0)
        // .findFirst()
        // .orElse(null);  
    }

    @Override
    public boolean save(Commande object) {
        return repo.insert(object);
    }

    @Override
    public List<Commande> show() {
        return repo.selectAll();
    }


    @Override
    public void effacer(Commande object) {
        repo.remove(object);
    }

    @Override
    public List<Commande> getByType(TypeCommande type) {
       return repo.selectByType(type);
    }

    @Override
    public boolean update(Commande object) {
        return repo.update(object);

    }
    @Override
    public List<Commande> getByClientId(int clientId) {
        return repo.selectAll().stream()
                   .filter(dette -> dette.getClient().getId() == clientId)
                   .toList();
    }
    
    
}

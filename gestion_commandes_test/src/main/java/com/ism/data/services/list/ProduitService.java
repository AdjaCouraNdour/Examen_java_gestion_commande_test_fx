package com.ism.data.services.list;

import java.util.List;

import com.ism.data.entities.Produit;
import com.ism.data.enums.EtatProduit;
import com.ism.data.repository.interfaces.ProduitRepositoryI;
import com.ism.data.services.interfaces.ProduitServiceI;

public class ProduitService implements ProduitServiceI {

    ProduitRepositoryI repo;

    public ProduitService(ProduitRepositoryI repo) {
        this.repo = repo;
    }

    @Override
    public Produit getById(int id) {
        return repo.selectById(id);
    }

    @Override
    public Produit getBy(EtatProduit etat) {
        return repo.selectBy(etat);    
    }

    @Override
    public boolean save(Produit object) {
        return repo.insert(object);
    }

    @Override
    public List<Produit> show() {
        return repo.selectAll();
    }

    public boolean update(Produit produit) {
        Produit art = getById(produit.getId());
    
        if (art != null) {
            art.setLibelle(produit.getLibelle());
            art.setQteStock(produit.getQteStock());
            art.setPrix(produit.getPrix());
            art.setEtatProduit(produit.getEtatProduit());
    
            return repo.update(art);  
        }
        return false; 
    }
    

    @Override
    public void effacer(Produit object) {
        repo.remove(object);
    }

    @Override
    public List<Produit> getByProduitEtat(EtatProduit etat) {
        return  repo.selectByEtat(etat);
    }
    
}

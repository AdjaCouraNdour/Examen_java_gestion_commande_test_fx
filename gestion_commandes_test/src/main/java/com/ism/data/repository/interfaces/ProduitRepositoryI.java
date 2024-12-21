package com.ism.data.repository.interfaces;


import java.util.List;

import com.ism.core.Repository.Repository;
import com.ism.data.entities.Produit;
import com.ism.data.enums.EtatProduit;

public interface ProduitRepositoryI extends Repository<Produit>{

    Produit selectById(int id) ;
    Produit selectBy(EtatProduit etat) ;
    // boolean update(Article article);
    List<Produit> selectByEtat(EtatProduit etat);

}

package com.ism.data.services.interfaces;

import java.util.List;

import com.ism.core.Services.Service;
import com.ism.data.entities.Produit;
import com.ism.data.enums.EtatProduit;

public interface ProduitServiceI extends Service<Produit>{
    Produit getBy(EtatProduit etat);
    // boolean mettreAJour(Article article);
    List<Produit> getByProduitEtat(EtatProduit etat);


}

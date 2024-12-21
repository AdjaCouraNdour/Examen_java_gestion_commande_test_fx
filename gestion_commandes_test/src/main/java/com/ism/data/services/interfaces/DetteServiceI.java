package com.ism.data.services.interfaces;

import java.util.List;

import com.ism.core.Services.Service;
import com.ism.data.entities.Commande;
import com.ism.data.enums.TypeCommande;

public interface DetteServiceI extends Service<Commande> {
    // Dette getById(int id) ;
    Commande getBy(TypeCommande etat);
    List<Commande> getByType(TypeCommande type);
    List<Commande> getByClientId(int clientId);

}

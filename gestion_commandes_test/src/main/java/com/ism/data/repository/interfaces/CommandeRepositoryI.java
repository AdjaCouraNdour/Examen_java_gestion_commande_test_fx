package com.ism.data.repository.interfaces;

import java.util.List;

import com.ism.core.Repository.Repository;
import com.ism.data.entities.Commande;
import com.ism.data.enums.TypeCommande;

public interface CommandeRepositoryI extends Repository<Commande> {

     Commande selectById(int id) ;
     Commande selectBy(TypeCommande type) ;
     List<Commande> selectByType(TypeCommande type);
     boolean update(Commande dette);

}

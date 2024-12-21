package com.ism.data.repository.jpa;


import java.util.List;
import com.ism.core.Repository.RepositoryJPA;
import com.ism.data.entities.Produit;
import com.ism.data.enums.EtatProduit;
import com.ism.data.repository.interfaces.ProduitRepositoryI;
import jakarta.persistence.TypedQuery;

public class ProduitRepositoryJPA extends RepositoryJPA<Produit> implements ProduitRepositoryI {

    public ProduitRepositoryJPA( Class<Produit> type) {
        super(type);
    }

     // Méthode pour sélectionner un Produit par son identifiant
    @Override
    public Produit selectById(int id) {
        try {
            return em.find(Produit.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Produit selectBy(EtatProduit etat) {
        try {
            TypedQuery<Produit> query = em.createQuery(
                "SELECT a FROM Produit a WHERE a.etatProduit = :etat", Produit.class);
            query.setParameter("etat", etat);
            return query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Produit> selectByEtat(EtatProduit etat) {
        try {
            TypedQuery<Produit> query = em.createQuery(
                "SELECT a FROM Produit a WHERE a.etatProduit = :etat", Produit.class);
            query.setParameter("etat", etat);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}

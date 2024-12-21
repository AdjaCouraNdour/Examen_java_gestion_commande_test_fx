package com.ism.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.ism.core.Factory.FactoryService;
import com.ism.data.entities.Produit;
import com.ism.data.enums.EtatProduit;

public class ProduitController {

    @FXML private TextField libelleField;
    @FXML private TextField prixField;
    @FXML private TextField qteStockField;
    @FXML private TextArea outputArea;
    @FXML private Button createProduitButton;
    @FXML private EtatProduit etatProduit;

    private FactoryService factoryService = new FactoryService();

    @FXML
    private void initialize() {
    }

    @FXML
    private void createProduit() {
        // Récupérer les données du formulaire
        String libelle = libelleField.getText();
        String prixString = prixField.getText();
        String qteStockString = qteStockField.getText();
        EtatProduit etatProduit = EtatProduit.Disponible;

        // Vérifier que les champs obligatoires ne sont pas vides
        if (libelle.isEmpty() || prixString.isEmpty() || qteStockString.isEmpty() || etatProduit == null) {
            outputArea.appendText("Tous les champs doivent être remplis.\n");
            return; // Sortir de la méthode si un champ est vide
        }

        // Convertir les valeurs numériques
        int prix;
        double qteStock;
        try {
            prix = Integer.parseInt(prixString);
            qteStock = Double.parseDouble(qteStockString);
        } catch (NumberFormatException e) {
            outputArea.appendText("Le prix et la quantité doivent être des nombres valides.\n");
            return;
        }

        // Créer un nouvel produit avec les données fournies
        Produit produit = new Produit();
        produit.setLibelle(libelle);
        produit.setPrix(prix);
        produit.setQteStock(qteStock);

        if ( produit.getQteStock()==0) {
            etatProduit = EtatProduit.Indisponible; 
        }
        produit.setEtatProduit(etatProduit);

        // Enregistrer l'Produit
        boolean success = enregistrerProduit(produit);

        if (success) {
            outputArea.appendText("produit créé avec succès.\n");
        } else {
            outputArea.appendText("Erreur lors de la création du produit.\n");
        }

        clearFields();
    }

    private void clearFields() {
        libelleField.clear();
        prixField.clear();
        qteStockField.clear();
    }

    public boolean enregistrerProduit(Produit produit) {
        if (produit == null) {
            return false;
        }

        factoryService.getInstanceProduitService().save(produit);

        return true;
    }
}

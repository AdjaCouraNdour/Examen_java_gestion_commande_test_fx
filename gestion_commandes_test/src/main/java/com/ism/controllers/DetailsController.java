package com.ism.controllers;

import com.ism.core.Factory.FactoryService;
import com.ism.data.entities.Details;
import com.ism.data.entities.Produit;
import com.ism.data.enums.EtatProduit;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.List;
import java.util.function.Consumer;

public class DetailsController {

    @FXML private ComboBox<Produit> produitComboBox; 
    @FXML private TextField qteField; 
    @FXML private Button createDetailsButton; 
    @FXML private TextArea outputArea; 
    private Consumer<Details> onDetailsCreated; 
    private FactoryService factoryService = new FactoryService(); 
    @FXML
    private void initialize() {
     
        loadAvailableProduits();
    }

    private void loadAvailableProduits() {
        List<Produit> produits = factoryService.getInstanceProduitService().getByProduitEtat(EtatProduit.Disponible);
        produitComboBox.getItems().addAll(produits);
    }

    @FXML
    private void createDetails() {
        Produit selectedProduit = produitComboBox.getValue();
        String qteText = qteField.getText();

        if (selectedProduit == null) {
            outputArea.appendText("Erreur : veuillez sélectionner un Produit.\n");
            return;
        }

        if (qteText.isEmpty()) {
            outputArea.appendText("Erreur : la quantité ne peut pas être vide.\n");
            return;
        }

        try {
            int qte = Integer.parseInt(qteText);
            if (qte <= 0 || qte > selectedProduit.getQteStock()) {
                outputArea.appendText("Erreur : la quantité doit être supérieure à 0 et inférieure ou égale au stock disponible.\n");
                return;
            }

            // Créer les détails
            Details details = new Details();
            details.setProduit(selectedProduit);
            details.setQteCommande(qte);

            // Enregistrer les détails
            enregistrerDetails(details);

            // Appeler le callback si défini
            if (onDetailsCreated != null) {
                onDetailsCreated.accept(details);
            }

            outputArea.appendText("Détails créés avec succès.\n");

        } catch (NumberFormatException e) {
            outputArea.appendText("Erreur : veuillez entrer une quantité valide.\n");
        }

        clearFields();
    }

    private void clearFields() {
        produitComboBox.setValue(null);
        qteField.clear();
    }

    public boolean enregistrerDetails(Details details) {
        if (details == null) {
            return false; 
        }

        factoryService.getInstanceDetailsService().save(details);
        return true; 
    }

    public void setOnDetailsCreated(Consumer<Details> onDetailsCreated) {
        this.onDetailsCreated = onDetailsCreated;
    }
}

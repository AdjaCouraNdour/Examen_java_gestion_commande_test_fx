package com.ism.controllers;

import com.ism.UserConnect;
import com.ism.core.Factory.FactoryService;
import com.ism.data.entities.Produit;
import com.ism.data.entities.Client;
import com.ism.data.entities.Details;
import com.ism.data.entities.Commande;
import com.ism.data.enums.TypeCommande;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class CommandeController {

    FactoryService factoryService =new FactoryService();
    private UserConnect connectedUser;

    private ObservableList<Details> addedProduits = FXCollections.observableArrayList();

    @FXML private ComboBox<Produit> produitComboBox;
    @FXML private TextField quantityField;
    @FXML private TextField clientNumeroField;
    @FXML private TextField clientNumberField;
    @FXML private TextField clientAdresseField;
    @FXML private TextField clientNameField;
    @FXML private TableView<Details> tabview;
    @FXML private TableColumn<Details, String> libelleColumn;
    @FXML private TableColumn<Details, Integer> qteCommandeColumn;
    @FXML private TableColumn<Details, Double> montantColumn;
    @FXML private Button addProduitButton;
    @FXML private Button createCommandeButton;
    @FXML private TextArea outputArea;


    public void setConnectedUser(UserConnect connectedUser) {
        this.connectedUser = connectedUser;
    }

    public CommandeController() {
    }
    @FXML
    public void initialize() {
        produitComboBox.getItems().addAll(factoryService.getInstanceProduitService().show());

        libelleColumn.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getProduit().getLibelle())
        );
        qteCommandeColumn.setCellValueFactory(new PropertyValueFactory<>("qteCommande"));
        montantColumn.setCellValueFactory(cellData ->
            new SimpleDoubleProperty(cellData.getValue().getProduit().getPrix() * cellData.getValue().getQteCommande()).asObject()
        );

        tabview.setItems(addedProduits);

        addProduitButton.setOnAction(e -> addProduitToTable());
        createCommandeButton.setOnAction(e -> createCommande());
    }
    
    @FXML   
    private void addProduitToTable() {
        Produit selectedProduit = produitComboBox.getValue();
        if (selectedProduit == null) {
            outputArea.appendText("Erreur : Aucun Produit sélectionné.\n");
            return;
        }

        String quantityText = quantityField.getText();
        int quantity;
        try {
            quantity = Integer.parseInt(quantityText);
            if (quantity <= 0) {
                outputArea.appendText("Erreur : La quantité doit être supérieure à 0.\n");
                return;
            } else if (quantity > selectedProduit.getQteStock()) {
                outputArea.appendText("Erreur : Quantité supérieure au stock disponible.\n");
                return;
            }
        } catch (NumberFormatException e) {
            outputArea.appendText("Erreur : Quantité invalide.\n");
            return;
        }

        Details existingDetail = null;
        for (Details detail : addedProduits) {
            if (detail.getProduit().equals(selectedProduit)) {
                existingDetail = detail;
                break;
            }
        }

        if (existingDetail != null) {
            existingDetail.setQteCommande(existingDetail.getQteCommande() + quantity);
            tabview.refresh();

        } else {
            Details detail = new Details();
            detail.setProduit(selectedProduit);
            detail.setQteCommande(quantity);
            addedProduits.add(detail);
        }

        produitComboBox.setValue(null);
        quantityField.clear();
    }


    @FXML   
    private void createCommande() {

        String clientNumber = clientNumberField.getText();
        if (clientNumber == null || clientNumber.trim().isEmpty()) {
            outputArea.appendText("Erreur : Veuillez entrer un numéro de client.\n");
            return;
        }
    
        Client client = factoryService.getInstanceClientService().getByNumero(clientNumber);
        if (client == null) {
            outputArea.appendText("Erreur : Aucun client associé à l'utilisateur connecté.\n");
            return;
        }

        if (addedProduits.isEmpty()) {
            outputArea.appendText("Erreur : Aucun Produit ajouté pour la Commande.\n");
            return;
        }

        double montantTotal = addedProduits.stream()
                                           .mapToDouble(d -> d.getProduit().getPrix() * d.getQteCommande())
                                           .sum();

        Commande commande = new Commande();
        commande.setClient(client);
        commande.setMontant(montantTotal);
        commande.setMontantVerse(0);
        commande.setMontantRestant(montantTotal);
        commande.setTypeCommande(TypeCommande.nonSolde);

        for (Details detail : addedProduits) {
            detail.setCommande(commande);
        }
        commande.setListeDetails(addedProduits);

        if (enregistrerCommande(commande)) {
            outputArea.appendText("commande créée avec succès.\n");
            addedProduits.clear();
            tabview.getItems().clear();
        } else {
            outputArea.appendText("Erreur lors de la création de la commande.\n");
        }
    }
    public boolean enregistrerCommande(Commande commande) {
        if (commande == null) {
            return false;
        }
        factoryService.getInstanceCommandeService().save(commande);
        for (Details detail : commande.getListeDetails()) {
            Produit produit = detail.getProduit();
            produit.setQteStock(produit.getQteStock() - detail.getQteCommande());
            factoryService.getInstanceProduitService().update(produit);
        }
    
        return true;
    }
    
    @FXML
    private void searchClient() {
        String clientNumber = clientNumberField.getText(); // Récupérer le numéro du client depuis le champ de texte

        if (clientNumber == null || clientNumber.trim().isEmpty()) {
            outputArea.appendText("Erreur : Veuillez entrer un numéro de client.\n");
            return;
        }

        Client client = factoryService.getInstanceClientService().getByNumero(clientNumber); 

        if (client == null) {
            outputArea.appendText("Erreur : Client non trouvé.\n");
        } else {
            clientNameField.setText(client.getNom()); 
            clientAdresseField.setText(client.getAddress()); 
            clientNumeroField.setText(client.getTelephone());

            outputArea.appendText("Client trouvé : " + client.getNom() + "\n");
        }
    }

}

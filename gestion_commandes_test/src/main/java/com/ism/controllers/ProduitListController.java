package com.ism.controllers;

import java.io.IOException;
import java.util.List;

import com.ism.core.Factory.FactoryService;
import com.ism.data.entities.Produit;
import com.ism.data.enums.EtatProduit;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;

public class ProduitListController {

    @FXML private TableView<Produit>tabview ;
    @FXML private TableColumn<Produit, String> libelle;
    @FXML private TableColumn<Produit, String> qteStock;
    @FXML private TableColumn<Produit, String> prix;
    @FXML private TableColumn<Produit, EtatProduit> etatProduit;

    
    private FactoryService factoryService ;


    public void initialize(){
        libelle.setCellValueFactory(new PropertyValueFactory<>("libelle"));
        qteStock.setCellValueFactory(new PropertyValueFactory<>("qteStock"));
        prix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        etatProduit.setCellValueFactory(new PropertyValueFactory<>("etatProduit"));
        factoryService= new FactoryService();
        loadTable();
    }

    public void loadTable(){
        List<Produit> produits = factoryService.getInstanceProduitService().show();
        System.out.println(produits);        
        ObservableList<Produit>produitList=FXCollections.observableArrayList(produits);
        tabview.setItems(produitList);
    };
    
      @FXML
    public void addProduit() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ism/produitForm.fxml"));
            Parent newContent = loader.load();

            // Récupérer le parent contenant `contentPane`
            Parent parentRoot = tabview.getScene().getRoot();
            VBox contentPane = (VBox) parentRoot.lookup("#contentPane");

            // Remplacer le contenu du panneau central
            if (contentPane != null) {
                contentPane.getChildren().setAll(newContent);
            } else {
                System.out.println("ContentPane non trouvé !");
            }
        } catch (IOException e) {
            e.printStackTrace(); // Gérer l'exception ici
            System.out.println("Erreur lors du chargement de la vue produitForm.fxml");
        }
    }
       
}







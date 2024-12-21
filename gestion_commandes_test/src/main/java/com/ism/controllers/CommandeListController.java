package com.ism.controllers;

import java.io.IOException;
import java.util.List;

import com.ism.UserConnect;
import com.ism.core.Factory.FactoryService;
import com.ism.data.entities.Client;
import com.ism.data.entities.Commande;
import com.ism.data.enums.TypeCommande;
import com.ism.data.enums.UserRole;

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
public class CommandeListController {

    @FXML private TableView<Commande>tabview ;
    @FXML private TableColumn<Commande, String> montant;
    @FXML private TableColumn<Commande, String> montantRestant;
    @FXML private TableColumn<Commande, Integer> id;
    @FXML private TableColumn<Commande, TypeCommande> typeCommande;
    private FactoryService factoryService ;
    private UserConnect connectedUser;

    public void setConnectedUser(UserConnect connectedUser) {
        this.connectedUser = connectedUser;
    }

    public void initialize(){
        montant.setCellValueFactory(new PropertyValueFactory<>("montant"));
        montantRestant.setCellValueFactory(new PropertyValueFactory<>("montantRestant"));
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        typeCommande.setCellValueFactory(new PropertyValueFactory<>("typeCommande"));

        factoryService= new FactoryService();
       loadTable();

    }

    public void loadTable(){
        Client connectedClient = connectedUser.getUserConnect().getClient();
        if (connectedUser.getUserConnect().getUserRole() == UserRole.Client) {
            List<Commande> Commandes = factoryService.getInstanceCommandeService().getByClientId(connectedClient.getId());
            System.out.println(Commandes);
            ObservableList<Commande>CommandeList=FXCollections.observableArrayList(Commandes);
            tabview.setItems(CommandeList);
        }else if (connectedUser.getUserConnect().getUserRole() == UserRole.Admin || connectedUser.getUserConnect().getUserRole() == UserRole.Boutiquier) {
            List<Commande> Commandes = factoryService.getInstanceCommandeService().show();
            System.out.println(Commandes);
            ObservableList<Commande>CommandeList=FXCollections.observableArrayList(Commandes);
            tabview.setItems(CommandeList);
        }
    };

    @FXML
    public void listUser() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ism/listerUsers.fxml"));
            Parent root = loader.load();
            
            // Remplacez la scène ou la fenêtre actuelle avec la nouvelle vue
            Stage stage = (Stage) tabview.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Gérez l'exception de manière appropriée
        }
    }
    @FXML
    public void addCommande() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ism/commandeForm.fxml"));
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
            System.out.println("Erreur lors du chargement de la vue commandeForm.fxml");
        }
    }
}







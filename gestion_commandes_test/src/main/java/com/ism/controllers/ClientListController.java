package com.ism.controllers;

import java.io.IOException;
import java.util.List;

import com.ism.core.Factory.FactoryService;
import com.ism.data.entities.Client;

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
public class ClientListController {

    @FXML private TableView<Client>tabview ;
    @FXML private TableColumn<Client, String> nom;
    @FXML private TableColumn<Client, String> prenom;
    @FXML private TableColumn<Client, String> adresse;
    @FXML private TableColumn<Client, String> telephone;
    // @FXML private TableColumn<Client, Integer> id;
    private FactoryService factoryService ;


    public void initialize(){
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        telephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        adresse.setCellValueFactory(new PropertyValueFactory<>("address"));
        // id.setCellValueFactory(new PropertyValueFactory<>("id"));
        factoryService= new FactoryService();
       loadTable();

    }

    public void loadTable(){
        List<Client> clients = factoryService.getInstanceClientService().show();
        System.out.println(clients);
        ObservableList<Client>clientList=FXCollections.observableArrayList(clients);
        tabview.setItems(clientList);
    };

    @FXML
    public void addClient() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ism/clientForm.fxml"));
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
            System.out.println("Erreur lors du chargement de la vue clientForm.fxml");
        }
    }


       
}
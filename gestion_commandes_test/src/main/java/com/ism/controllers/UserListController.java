package com.ism.controllers;

import java.io.IOException;
import java.util.List;

import com.ism.core.Factory.FactoryService;
import com.ism.data.entities.User;
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
public class UserListController {

    @FXML private TableView<User>tabview ;
    @FXML private TableColumn<User, String> login;
    @FXML private TableColumn<User, String> email;
    @FXML private TableColumn<User, Boolean> actif;
    @FXML private TableColumn<User, UserRole> userRole;
    private FactoryService factoryService ;


    public void initialize(){
        login.setCellValueFactory(new PropertyValueFactory<>("login"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        userRole.setCellValueFactory(new PropertyValueFactory<>("userRole"));
        actif.setCellValueFactory(new PropertyValueFactory<>("actif"));
        // id.setCellValueFactory(new PropertyValueFactory<>("id"));
        factoryService= new FactoryService();
       loadTable();

    }

    public void loadTable(){
        List<User> Users = factoryService.getInstanceUserService().show();
        System.out.println(Users);
        ObservableList<User>UserList=FXCollections.observableArrayList(Users);
        tabview.setItems(UserList);
    };

        @FXML
    public void addUser() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ism/userForm.fxml"));
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
            System.out.println("Erreur lors du chargement de la vue userForm.fxml");
        }
    }
}
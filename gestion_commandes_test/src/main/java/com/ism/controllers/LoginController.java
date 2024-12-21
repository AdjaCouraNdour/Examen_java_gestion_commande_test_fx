package com.ism.controllers;

import java.io.IOException;
import com.ism.App;
import com.ism.UserConnect;
import com.ism.core.Factory.FactoryService;
import com.ism.data.entities.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

  @FXML
  private TextField usernameField;
  @FXML
  private PasswordField passwordField;
  @FXML
  private Button loginButton;

  private FactoryService factoryService = new FactoryService();

  public void setFactoryService(FactoryService factoryService) {
      this.factoryService = factoryService;
  }
  
  public LoginController(FactoryService factoryService) {
      this.factoryService = factoryService;
  }
  
  public LoginController(){
  }

  @FXML
  public void initialize() {
    loginButton.setOnAction(event -> login());
  }

  @FXML
  private void login() { 
    
    String login = usernameField.getText();
    String password= passwordField.getText();
    User userConnect = null;

  
    // Vérification des champs
    if (login.isEmpty() || password.isEmpty()) {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Erreur");
      alert.setContentText("Veuillez remplir tous les champs.");
      alert.showAndWait();
      return; // On arrête l'exécution si les champs sont vides
    }

    // Connexion
    userConnect = factoryService.getInstanceUserService().validateUser(login, password);
    // UserService userService = factoryService.getInstanceUserService();

    if (userConnect != null) {
      System.out.println(userConnect);

      UserConnect.setUserConnect(userConnect);

      switch (userConnect.getUserRole()) {

        case Admin:
          try {
            System.out.println("Chargement du menu admin...");
            App.setRoot("menuAdmin");
          } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de chargement");
            alert.setContentText("Impossible de charger l'interface admin.");
            alert.showAndWait();
          }
          break;

        case Boutiquier:
          try {
            App.setRoot("menuBoutiquier");
          } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de chargement");
            alert.setContentText("Impossible de charger l'interface boutiquier.");
            alert.showAndWait();
          }
          break;

        case Client:
          try {
            App.setRoot("menuClient");
          } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de chargement");
            alert.setContentText("Impossible de charger l'interface client.");
            alert.showAndWait();
          }
        default:
          break;
      }
    } else {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Erreur de connexion");
      alert.setContentText("Login ou mot de passe incorrect.");
      alert.showAndWait();
    }
  }
  
  
}
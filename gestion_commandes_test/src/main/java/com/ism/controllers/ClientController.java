package com.ism.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.function.Consumer;
import com.ism.core.Factory.FactoryService;
import com.ism.data.entities.Client;
import com.ism.data.entities.User;
import com.ism.data.enums.UserRole;

public class ClientController {

    @FXML private TextField nomField;
    @FXML private TextField prenomField;
    @FXML private TextField telephoneField;
    @FXML private TextField adresseField;
    @FXML private TextArea outputArea;
    @FXML private Button createClientButton;
    @FXML private CheckBox associateUserCheckBox;

    // Champs supplémentaires pour l'utilisateur
    @FXML private TextField emailField;
    @FXML private TextField loginField;
    @FXML private PasswordField passwordField;
    // @FXML private ComboBox<UserRole> roleComboBox;
    private Consumer<Client> onClientCreated;
    private FactoryService factoryService = new FactoryService();

    @FXML
    private void initialize() {
        // Configuration initiale
        // roleComboBox.getItems().setAll(UserRole.values());
        // roleComboBox.setVisible(false); 
        associateUserCheckBox.setOnAction(e -> toggleUserFields());
    }

    private void toggleUserFields() {
        if(associateUserCheckBox.isSelected()){
            emailField.setDisable(false);
            loginField.setDisable(false);
            passwordField.setDisable(false);
            // roleComboBox.setDisable(false);
        }else{
            emailField.setDisable(true);
            loginField.setDisable(true);
            passwordField.setDisable(true);
            // roleComboBox.setDisable(true);
        };
       
    }

    @FXML
    private void createClient() {
        // Récupérer les données du formulaire client
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String telephone = telephoneField.getText();
        String adresse = adresseField.getText();
    
        // Vérifier que les champs obligatoires ne sont pas vides
        if (nom.isEmpty() || prenom.isEmpty() || telephone.isEmpty() || adresse.isEmpty()) {
            outputArea.appendText("Tous les champs du client doivent être remplis.\n");
            return; // Sortir de la méthode si un champ est vide
        }
    
        Client client = new Client();
        client.setNom(nom);
        client.setPrenom(prenom);
        client.setTelephone(telephone);
        client.setAddress(adresse);
    
        User user = null;
        if (associateUserCheckBox.isSelected()) {
            // Créer un utilisateur avec les données fournies
            String email = emailField.getText();
            String login = loginField.getText();
            String password = passwordField.getText();
            UserRole role = UserRole.Client;
    
            // Vérifier que les champs utilisateur ne sont pas vides
            if (email.isEmpty() || login.isEmpty() || password.isEmpty() || role == null) {
                outputArea.appendText("Tous les champs de l'utilisateur doivent être remplis.\n");
                return; // Sortir de la méthode si un champ utilisateur est vide
            }
    
            user = new User();
            user.setEmail(email);
            user.setLogin(login);
            user.setPassword(password);
            user.setUserRole(role);
        }
    
        // Enregistrer le client et, si applicable, l'utilisateur associé
        boolean success = enregistrerClient(client, user);
    
        if (onClientCreated != null) {
            onClientCreated.accept(client);
        }
    
        if (success) {
            outputArea.appendText("Client et utilisateur créés avec succès.\n");
        } else {
            outputArea.appendText("Erreur lors de la création du client ou de l'utilisateur.\n");
        }
    
        // Réinitialiser les champs après la création
        clearFields();
    }

    private void clearFields() {
        nomField.clear();
        prenomField.clear();
        telephoneField.clear();
        adresseField.clear();
        associateUserCheckBox.setSelected(false);

        // Réinitialiser les champs utilisateur si visibles
        if (emailField != null) emailField.clear();
        if (loginField != null) loginField.clear();
        if (passwordField != null) passwordField.clear();
        // if (roleComboBox != null) roleComboBox.setValue(null);
    }

    public boolean enregistrerClient(Client client, User user) {
        if (client == null) {
            return false; 
        }
        if (user != null) {
            factoryService.getInstanceUserService().save(user);
            client.setUser(user);
        }
        factoryService.getInstanceClientService().save(client);
        return true; 
    }
    

    public void setOnClientCreated(Consumer<Client> onClientCreated) {
        this.onClientCreated = onClientCreated;
    }

}

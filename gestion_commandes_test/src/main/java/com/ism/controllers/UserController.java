package com.ism.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.ism.data.entities.User;
import com.ism.data.enums.UserRole;
import com.ism.core.Factory.FactoryService;

public class UserController {

    @FXML private TextField loginField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private ComboBox<UserRole> roleComboBox;
    @FXML private TextArea outputArea;
    @FXML private Button createUserButton;

    private FactoryService factoryService = new FactoryService();

    @FXML
    private void initialize() {
        // Initialiser les valeurs de la ComboBox avec les rôles disponibles
        roleComboBox.getItems().setAll(UserRole.values());

    }

    @FXML
    private void createUser() {
        // Récupérer les données du formulaire
        String login = loginField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        UserRole role = roleComboBox.getValue();
        // boolean actif = actifCheckBox.isSelected();

        // Vérification des champs obligatoires
        if (login.isEmpty() || email.isEmpty() || password.isEmpty() || role == null) {
            outputArea.appendText("Tous les champs doivent être remplis.\n");
            return;
        }

        // Création de l'utilisateur
        User user = new User();
        user.setLogin(login);
        user.setEmail(email);
        user.setPassword(password);
        user.setUserRole(role);
        user.setActif(true);

        // Enregistrement de l'utilisateur
        boolean success = enregistrerUser(user);

        if (success) {
            outputArea.appendText("Utilisateur créé avec succès.\n");
        } else {
            outputArea.appendText("Erreur lors de la création de l'utilisateur.\n");
        }

        // Réinitialiser les champs après la création
        clearFields();
    }

    private boolean enregistrerUser(User user) {
        if (user == null) {
            return false; 
        }
        try {
            factoryService.getInstanceUserService().save(user); 
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

/*************  ✨ Codeium Command ⭐  *************/
    /**
/******  5a57a20a-81ba-490d-a191-1f4b918a62bf  *******/
    private void clearFields() {
        loginField.clear();
        emailField.clear();
        passwordField.clear();
        roleComboBox.setValue(null);
    }
}

package com.ism.controllers;

import java.io.IOException;
import com.ism.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MenuController {
    @FXML
    private VBox contentPane;

    @FXML
    private void logout() {
        try {
            System.out.println("Déconnexion en cours...");
            App.setRoot("connexion");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la déconnexion !");
        }
    }

   @FXML
    private void listClient() {
        loadView("ListerClients");
    }

    @FXML
    private void listProduit() {
        loadView("listerProduits");
    }

    @FXML
    private void listCommande() {
        loadView("listerCommandes");
    }
   
    private void loadView(String fxml) {
        try {
            // Charge dynamiquement une nouvelle vue dans contentPane
            Pane pane = FXMLLoader.load(getClass().getResource("/com/ism/" + fxml + ".fxml"));
            contentPane.getChildren().setAll(pane);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur lors du chargement de la vue : " + fxml);
        }
    }
     @FXML
    public void addCommande() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ism/commandeForm.fxml"));
            Parent newContent = loader.load();
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

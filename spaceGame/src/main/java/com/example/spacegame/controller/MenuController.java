package com.example.spacegame.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Contrôleur pour la vue du menu principal.
 */
public class MenuController {

    @FXML
    private Button startButton;

    @FXML
    private Button levelButton;

    @FXML
    private Button exitButton;

    /**
     * Initialise le contrôleur du menu.
     */
    @FXML
    public void initialize() {
        // Initialisation si nécessaire
    }

    /**
     * Gère l'événement du bouton de démarrage du jeu.
     * @param event L'événement d'action.
     */
    @FXML
    private void startGame(ActionEvent event) {
        try {
            // Chargement du FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/game-view.fxml"));
            Parent gameRoot = loader.load();

            // Récupération du contrôleur de jeu
            GameController gameController = loader.getController();

            // Nouvelle scène
            Scene gameScene = new Scene(gameRoot);

            // Récupération de la fenêtre courante
            Stage stage = (Stage) startButton.getScene().getWindow();
            stage.setScene(gameScene);
            stage.show();

            // Focus
            gameRoot.requestFocus();

            // Initialisation du contrôleur de jeu après avoir affiché la scène
            // pour que les handlers de clavier soient bien configurés
            gameController.initializeGame(1);  // niveau 1
            gameController.setupKeyboardHandlers();

        } catch (IOException e) {
            e.printStackTrace();

            // Affiche une alerte utilisateur en cas d'erreur
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
            alert.setTitle("Erreur de chargement");
            alert.setHeaderText("Impossible de charger la vue du jeu");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }



    /**
     * Gère l'événement du bouton de sélection de niveau.
     * @param event L'événement d'action.
     */
    @FXML
    private void selectLevel(ActionEvent event) {
        System.out.println("Chemin du FXML : " + getClass().getResource("/fxml/level-selection.fxml"));

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/level-selection.fxml"));
            Parent levelRoot = loader.load();

            Scene levelScene = new Scene(levelRoot);
            Stage stage = (Stage) levelButton.getScene().getWindow();
            stage.setScene(levelScene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
            alert.setTitle("Erreur de chargement");
            alert.setHeaderText("Impossible de charger la vue de sélection de niveau");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }


    /**
     * Gère l'événement du bouton de sortie du jeu.
     * @param event L'événement d'action.
     */
    @FXML
    private void exitGame(ActionEvent event) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quitter");
        alert.setHeaderText("Confirmation de sortie");
        alert.setContentText("Es-tu sûr de vouloir quitter le jeu ?");

        if (alert.showAndWait().get() == javafx.scene.control.ButtonType.OK) {
            Stage stage = (Stage) exitButton.getScene().getWindow();
            stage.close();
        }
    }

}
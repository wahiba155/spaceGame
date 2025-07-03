package com.example.spacegame.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.IOException;

public class LevelSelectionController {

    public LevelSelectionController() {
        System.out.println("LevelSelectionController créé");
    }

    private void loadLevel(ActionEvent event, int level) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/game-view.fxml"));
            Parent gameRoot = loader.load();

            // Récupère le contrôleur pour appeler initializeGame(level)
            GameController gameController = loader.getController();
            gameController.initializeGame(level);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(gameRoot));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void startLevel1(ActionEvent event) {
        System.out.println("Niveau 1 sélectionné");
        loadLevel(event, 1);
    }

    @FXML
    private void startLevel2(ActionEvent event) {
        System.out.println("Niveau 2 sélectionné");
        loadLevel(event, 2);
    }

    @FXML
    private void startLevel3(ActionEvent event) {
        System.out.println("Niveau 3 sélectionné");
        loadLevel(event, 3);
    }


    @FXML
    private void returnToMenu(ActionEvent event) {
        System.out.println("Retour au menu principal");

        try {
            // Charge la vue du menu principal
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/menu-view.fxml"));
            Parent menuRoot = loader.load();

            // Récupère la fenêtre actuelle depuis l'événement
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Change la scène
            stage.setScene(new Scene(menuRoot));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

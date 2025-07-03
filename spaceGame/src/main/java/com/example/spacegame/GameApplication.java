package com.example.spacegame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Classe principale qui lance l'application du jeu de l'espace
 */
public class GameApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Charger le fichier FXML du menu
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/menu-view.fxml"));
        Parent root = loader.load();

        // Configurer la scène
        Scene scene = new Scene(root);

        // Configurer la fenêtre principale
        primaryStage.setTitle("Jeu de l'Espace");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
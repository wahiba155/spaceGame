
module com.example.spacegame {
        requires javafx.controls;
        requires javafx.fxml;
    requires java.desktop;

    // Ajoutez cette ligne pour résoudre l'erreur
        exports com.example.spacegame.controller to javafx.fxml;

        // Si vous avez déjà une directive "opens", assurez-vous aussi d'ouvrir le package
        opens com.example.spacegame.controller to javafx.fxml;

        // Gardez vos autres directives exports existantes
        exports com.example.spacegame;
}
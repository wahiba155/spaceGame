package com.example.spacegame.controller;

import com.example.spacegame.model.*;
import com.example.spacegame.utils.CollisionDetector;
import com.example.spacegame.utils.GameConstants;
import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class GameController {

    @FXML
    private Button leftButton;

    @FXML
    private Button rightButton;


    private boolean inTransition = false;

    @FXML
    private Pane gamePane;

    @FXML
    private Label scoreLabel;

    @FXML
    private HBox livesContainer;

    @FXML
    private ImageView playerImageView;


    private Player player;
    private final List<Enemy> enemies = new ArrayList<>();
    private final List<Projectile> projectiles = new ArrayList<>();
    private final List<Bomb> bombs = new ArrayList<>();
    private final List<Explosion> explosions = new ArrayList<>();

    private final CollisionDetector collisionDetector = new CollisionDetector();
    private final Random random = new Random();
    private AnimationTimer gameLoop;

    private int currentLevel;
    private int score;
    private boolean gameOver;
    private boolean leftKeyPressed;
    private boolean rightKeyPressed;
    private long lastProjectileTime;
    private long enemySpawnDelay;
    private long lastEnemySpawnTime;
    @FXML
    private ImageView backgroundImageView;


    @FXML
    public void initialize() {
        // Initialisation faite via initializeGame()
    }

    public void initializeGame(int level) {
        currentLevel = level;
        score = 0;
        gameOver = false;
        scoreLabel.setText("0");

        enemies.clear();
        projectiles.clear();
        bombs.clear();
        explosions.clear();

        // 🔽 Crée et ajoute l’image de fond si elle n’existe pas encore
        ImageView backgroundImage = new ImageView(new Image(getClass().getResourceAsStream("/images/background.png")));
        backgroundImage.setFitWidth(gamePane.getPrefWidth());
        backgroundImage.setFitHeight(gamePane.getPrefHeight());
        backgroundImage.setPreserveRatio(false);

        // 👉 L’ajouter tout au fond s’il n’est pas encore là
        if (!gamePane.getChildren().contains(backgroundImage)) {
            gamePane.getChildren().add(0, backgroundImage); // index 0 = en arrière-plan
        }

        player = new Player(gamePane.getPrefWidth() / 2, gamePane.getPrefHeight() - 100);
        playerImageView.setImage(new Image(getClass().getResourceAsStream("/images/player.png")));
        playerImageView.setFitWidth(50);
        playerImageView.setFitHeight(50);
        playerImageView.setLayoutX(player.getX());
        playerImageView.setLayoutY(player.getY());
        player.setView(playerImageView);

        if (!gamePane.getChildren().contains(playerImageView)) {
            gamePane.getChildren().add(playerImageView); // 🔁 important
        }
        updateLivesDisplay();
        configureLevel(level);
        showLevelTransition(level); // ⬅️ affiche "Niveau 1", "Niveau 2", etc.

        // 👉 On retarde l'apparition des ennemis jusqu'à ce que le gamePane soit prêt
        Platform.runLater(() -> {
            spawnEnemiesForLevel(level);
        });

        startGameLoop();

        // Pour que les touches clavier soient bien capturées après affichage
        Platform.runLater(this::setupKeyboardHandlers);
    }



    private void configureLevel(int level) {
        switch (level) {
            case 1 -> enemySpawnDelay = 2000;
            case 2 -> enemySpawnDelay = 1500;
            case 3 -> enemySpawnDelay = 1000;
            default -> enemySpawnDelay = 2000;
        }
    }
    private void spawnEnemiesForLevel(int level) {
        enemies.clear(); // on s'assure que la liste est vide avant d'ajouter

        switch (level) {
            case 1 -> {
                for (int i = 0; i < 5; i++) {
                    Enemy enemy = new Enemy(50 + i * 60, 50);
                    setEnemyImage(enemy, "/images/enemy.png");
                    enemies.add(enemy);
                    gamePane.getChildren().add(enemy.getView());
                }
            }
            case 2 -> {
                for (int i = 0; i < 8; i++) {
                    Enemy enemy = new Enemy(30 + i * 50, 50);
                    setEnemyImage(enemy, "/images/enemy.png");
                    enemies.add(enemy);
                    gamePane.getChildren().add(enemy.getView());
                }
            }
            case 3 -> {
                for (int i = 0; i < 6; i++) {
                    Enemy normalEnemy = new Enemy(20 + i * 40, 50);
                    setEnemyImage(normalEnemy, "/images/enemy.png");
                    enemies.add(normalEnemy);
                    gamePane.getChildren().add(normalEnemy.getView());
                }

                for (int i = 0; i < 3; i++) {
                    Enemy specialEnemy = new Enemy(40 + i * 100, 120);
                    setEnemyImage(specialEnemy, "/images/enemy2.png"); // une nouvelle image
                    enemies.add(specialEnemy);
                    gamePane.getChildren().add(specialEnemy.getView());
                }
            }
        }
    }







    public void setupKeyboardHandlers() {
        gamePane.getScene().setOnKeyPressed(this::handleKeyPressed);
        gamePane.getScene().setOnKeyReleased(this::handleKeyReleased);
    }

    private void handleKeyPressed(KeyEvent event) {
        if (gameOver) return;
        KeyCode code = event.getCode();

        if (code == KeyCode.LEFT) {
            leftKeyPressed = true;
        } else if (code == KeyCode.RIGHT) {
            rightKeyPressed = true;
        } else if (code == KeyCode.SPACE) {
            fireProjectile();
        }
    }

    private void handleKeyReleased(KeyEvent event) {
        KeyCode code = event.getCode();

        if (code == KeyCode.LEFT) {
            leftKeyPressed = false;
        } else if (code == KeyCode.RIGHT) {
            rightKeyPressed = false;
        }

        if (event.getCode() == KeyCode.SPACE) {
            Projectile projectile = player.shoot();
            if (projectile != null) {
                projectiles.add(projectile);
                gamePane.getChildren().add(projectile.getView());
            }
        }

    }

    private void startGameLoop() {
        if (gameLoop != null) gameLoop.stop();

        lastEnemySpawnTime = System.currentTimeMillis();

        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (!gameOver) updateGame();
            }
        };
        gameLoop.start();
    }

    @FXML
    private void onFireButtonClick() {
        fireProjectile(); // même méthode que la barre d'espace
    }


    private void updateGame() {
        long currentTime = System.currentTimeMillis();

        updatePlayer();

        if (currentTime - lastEnemySpawnTime > enemySpawnDelay) {
            spawnEnemy();
            lastEnemySpawnTime = currentTime;
        }

        updateEnemies();
        updateProjectiles();
        updateBombs();
        updateExplosions();
        checkCollisions();




    }

    private void updatePlayer() {
        if (leftKeyPressed && player.getX() > 0) player.moveLeft();
        if (rightKeyPressed && player.getX() < gamePane.getPrefWidth() - playerImageView.getFitWidth()) player.moveRight();

        playerImageView.setLayoutX(player.getX());
        playerImageView.setLayoutY(player.getY());
    }

    private void fireProjectile() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastProjectileTime > GameConstants.PROJECTILE_COOLDOWN) {
            Projectile projectile = new Projectile(
                    player.getX() + playerImageView.getFitWidth() / 2 - 5,
                    player.getY()
            );
            projectiles.add(projectile);

            ImageView view = new ImageView(new Image(getClass().getResourceAsStream("/images/projectile.png")));
            view.setFitWidth(10);
            view.setFitHeight(20);
            view.setLayoutX(projectile.getX());
            view.setLayoutY(projectile.getY());
            projectile.setView(view);
            gamePane.getChildren().add(view);

            lastProjectileTime = currentTime;
        }
    }

    private void showLevelTransition(int level) {
        Label levelLabel = new Label("Niveau " + level);
        levelLabel.setStyle("-fx-font-size: 36px; -fx-text-fill: white;");
        levelLabel.setLayoutX(gamePane.getPrefWidth() / 2 - 100);
        levelLabel.setLayoutY(gamePane.getPrefHeight() / 2 - 50);
        gamePane.getChildren().add(levelLabel);

        // Petite pause avant de continuer
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                javafx.application.Platform.runLater(() -> {
                    gamePane.getChildren().remove(levelLabel);
                    lastEnemySpawnTime = System.currentTimeMillis();
                });
            }
        }, 2000);
    }


    private void spawnEnemy() {
        double x = random.nextDouble() * (gamePane.getPrefWidth() - 50);
        Enemy enemy = new Enemy(x, 0);
        enemies.add(enemy);

        ImageView view = new ImageView(new Image(getClass().getResourceAsStream("/images/enemy.png")));
        view.setFitWidth(50);
        view.setFitHeight(50);
        view.setLayoutX(enemy.getX());
        view.setLayoutY(enemy.getY());
        enemy.setView(view);
        gamePane.getChildren().add(view);
    }

    private void updateEnemies() {
        Iterator<Enemy> it = enemies.iterator();
        while (it.hasNext()) {
            Enemy enemy = it.next();
            enemy.move();

            if (enemy.getY() > gamePane.getPrefHeight()) {
                gamePane.getChildren().remove(enemy.getView());
                it.remove();
                continue;
            }

            enemy.getView().setLayoutX(enemy.getX());
            enemy.getView().setLayoutY(enemy.getY());

            if (random.nextDouble() < GameConstants.BOMB_DROP_CHANCE) {
                dropBomb(enemy);
            }
        }
    }

    private void dropBomb(Enemy enemy) {
        Bomb bomb = new Bomb(
                enemy.getX() + enemy.getView().getFitWidth() / 2 - 5,
                enemy.getY() + enemy.getView().getFitHeight()
        );
        bombs.add(bomb);

        ImageView view = new ImageView(new Image(getClass().getResourceAsStream("/images/bomb.png")));
        view.setFitWidth(10);
        view.setFitHeight(20);
        view.setLayoutX(bomb.getX());
        view.setLayoutY(bomb.getY());
        bomb.setView(view);
        gamePane.getChildren().add(view);
    }

    private void updateProjectiles() {
        Iterator<Projectile> it = projectiles.iterator();
        while (it.hasNext()) {
            Projectile p = it.next();
            p.move();
            if (p.getY() < 0) {
                gamePane.getChildren().remove(p.getView());
                it.remove();
                continue;
            }
            p.getView().setLayoutX(p.getX());
            p.getView().setLayoutY(p.getY());
        }
    }

    private void updateBombs() {
        Iterator<Bomb> it = bombs.iterator();
        while (it.hasNext()) {
            Bomb b = it.next();
            b.move();
            if (b.getY() > gamePane.getPrefHeight()) {
                gamePane.getChildren().remove(b.getView());
                it.remove();
                continue;
            }
            b.getView().setLayoutX(b.getX());
            b.getView().setLayoutY(b.getY());
        }
    }

    private void updateExplosions() {
        Iterator<Explosion> it = explosions.iterator();
        while (it.hasNext()) {
            Explosion e = it.next();
            e.update();
            if (e.isFinished()) {
                gamePane.getChildren().remove(e.getView());
                it.remove();
            }
        }
    }

    private void checkCollisions() {
        Iterator<Projectile> pit = projectiles.iterator();
        while (pit.hasNext()) {
            Projectile p = pit.next();
            Iterator<Enemy> eit = enemies.iterator();
            boolean hit = false;

            while (eit.hasNext() && !hit) {
                Enemy e = eit.next();
                if (collisionDetector.checkCollision(p, e)) {
                    createExplosion(e.getX(), e.getY());
                    gamePane.getChildren().remove(e.getView());
                    gamePane.getChildren().remove(p.getView());
                    eit.remove();
                    pit.remove();
                    score += 10;
                    scoreLabel.setText(String.valueOf(score));
                    hit = true;
                }
            }
        }

        Iterator<Bomb> bit = bombs.iterator();
        while (bit.hasNext()) {
            Bomb b = bit.next();
            if (collisionDetector.checkCollision(b, player)) {
                createExplosion(player.getX(), player.getY());
                gamePane.getChildren().remove(b.getView());
                bit.remove();
                player.decreaseLives();
                updateLivesDisplay();

                if (player.getLives() <= 0) {
                    endGame();
                }
            }
        }
    }
    private void setEnemyImage(Enemy enemy, String imagePath) {
        ImageView view = new ImageView(new Image(getClass().getResourceAsStream(imagePath)));
        view.setFitWidth(50);
        view.setFitHeight(50);
        view.setLayoutX(enemy.getX());
        view.setLayoutY(enemy.getY());
        enemy.setView(view);
    }


    private void createExplosion(double x, double y) {
        Explosion explosion = new Explosion(x, y);
        ImageView view = new ImageView(new Image(getClass().getResourceAsStream("/images/explosion.png")));
        view.setFitWidth(50);
        view.setFitHeight(50);
        view.setLayoutX(x);
        view.setLayoutY(y);
        explosion.setView(view);
        explosions.add(explosion);
        gamePane.getChildren().add(view);
    }

    private void updateLivesDisplay() {
        livesContainer.getChildren().clear();
        for (int i = 0; i < player.getLives(); i++) {
            ImageView heart = new ImageView(new Image(getClass().getResourceAsStream("/images/heart.png")));
            heart.setFitWidth(30);
            heart.setFitHeight(30);
            livesContainer.getChildren().add(heart);
        }
    }
    private void clearGameObjects() {
        // Supprime les éléments dynamiques uniquement
        gamePane.getChildren().removeIf(node ->
                node != playerImageView &&
                        !(node instanceof Button) &&
                        !(node instanceof Label)
        );
    }


    private void endGame() {
        gameOver = true;
        if (gameLoop != null) {
            gameLoop.stop();
        }

        Label gameOverLabel = new Label("GAME OVER");
        gameOverLabel.setTextFill(Color.RED);
        gameOverLabel.setFont(new Font(40));
        gameOverLabel.setLayoutX(250);
        gameOverLabel.setLayoutY(200);

        Button menuButton = new Button("Menu Principal");
        menuButton.setLayoutX(260);
        menuButton.setLayoutY(270);

        Button replayButton = new Button("Rejouer");
        replayButton.setLayoutX(360);
        replayButton.setLayoutY(270);

        replayButton.setOnAction(e -> {
            // Supprimer les éléments visuels liés au game over
            gamePane.getChildren().removeAll(gameOverLabel, menuButton, replayButton);

            // Nettoyer les entités du jeu
            clearGameObjects();

            // Redémarrer au niveau 1
            initializeGame(currentLevel);
        });



        // Action pour retourner au menu principal
        menuButton.setOnAction(e -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/menu-view.fxml")); // Vérifie le chemin
                Parent menuRoot = loader.load();
                Scene menuScene = new Scene(menuRoot);
                Stage stage = (Stage) gamePane.getScene().getWindow();
                stage.setScene(menuScene);
                stage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        gamePane.getChildren().addAll(gameOverLabel, menuButton, replayButton);
    }

    @FXML
    private void onLeftButtonPressed() {
        System.out.println("Left button pressed");
        if (!gameOver) {
            player.moveLeft();
            updatePlayerView();
        }
    }

    @FXML
    private void onRightButtonPressed() {
        System.out.println("Right button pressed");
        if (!gameOver) {
            player.moveRight();
            updatePlayerView();
        }
    }


    private void updatePlayerView() {
        playerImageView.setLayoutX(player.getX());
        playerImageView.setLayoutY(player.getY());
    }




}
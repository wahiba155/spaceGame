package com.example.spacegame.model;

/**
 * Classe représentant un niveau de jeu avec ses paramètres
 */
public class GameLevel {

    private int level;
    private int enemySpawnRate;
    private double enemySpeed;
    private double bombDropChance;
    private String backgroundImage;

    /**
     * Constructeur du niveau
     * @param level Numéro du niveau
     */
    public GameLevel(int level) {
        this.level = level;
        configureLevel();
    }

    /**
     * Configure les paramètres du niveau en fonction de sa difficulté
     */
    private void configureLevel() {
        switch (level) {
            case 1:
                // Niveau facile
                enemySpawnRate = 2000; // 2 secondes
                enemySpeed = 1.0;
                bombDropChance = 0.01; // 1%
                backgroundImage = "/images/background-1.png";
                break;

            case 2:
                // Niveau moyen
                enemySpawnRate = 1500; // 1.5 secondes
                enemySpeed = 1.5;
                bombDropChance = 0.02; // 2%
                backgroundImage = "/images/background-2.png";
                break;

            case 3:
                // Niveau difficile
                enemySpawnRate = 1000; // 1 seconde
                enemySpeed = 2.0;
                bombDropChance = 0.03; // 3%
                backgroundImage = "/images/background-3.png";
                break;

            default:
                // Niveau par défaut (facile)
                enemySpawnRate = 2000;
                enemySpeed = 1.0;
                bombDropChance = 0.01;
                backgroundImage = "/images/background-1.png";
        }
    }

    /**
     * Obtient le numéro du niveau
     * @return Numéro du niveau
     */
    public int getLevel() {
        return level;
    }

    /**
     * Définit le numéro du niveau et reconfigure ses paramètres
     * @param level Nouveau numéro de niveau
     */
    public void setLevel(int level) {
        this.level = level;
        configureLevel();
    }

    /**
     * Obtient le taux d'apparition des ennemis en millisecondes
     * @return Taux d'apparition
     */
    public int getEnemySpawnRate() {
        return enemySpawnRate;
    }

    /**
     * Définit le taux d'apparition des ennemis
     * @param enemySpawnRate Nouveau taux d'apparition
     */
    public void setEnemySpawnRate(int enemySpawnRate) {
        this.enemySpawnRate = enemySpawnRate;
    }

    /**
     * Obtient la vitesse des ennemis
     * @return Vitesse
     */
    public double getEnemySpeed() {
        return enemySpeed;
    }

    /**
     * Définit la vitesse des ennemis
     * @param enemySpeed Nouvelle vitesse
     */
    public void setEnemySpeed(double enemySpeed) {
        this.enemySpeed = enemySpeed;
    }

    /**
     * Obtient la probabilité qu'un ennemi largue une bombe
     * @return Probabilité
     */
    public double getBombDropChance() {
        return bombDropChance;
    }

    /**
     * Définit la probabilité qu'un ennemi largue une bombe
     * @param bombDropChance Nouvelle probabilité
     */
    public void setBombDropChance(double bombDropChance) {
        this.bombDropChance = bombDropChance;
    }

    /**
     * Obtient le chemin de l'image de fond
     * @return Chemin de l'image
     */
    public String getBackgroundImage() {
        return backgroundImage;
    }

    /**
     * Définit le chemin de l'image de fond
     * @param backgroundImage Nouveau chemin
     */
    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }
}
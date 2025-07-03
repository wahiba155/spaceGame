package com.example.spacegame.model;

import com.example.spacegame.utils.GameConstants;

import java.util.Random;

/**
 * Classe représentant un ennemi
 */
public class Enemy extends GameObject {

    private double speedX;
    private double speedY;
    private Random random;

    /**
     * Constructeur de l'ennemi
     * @param x Position X initiale
     * @param y Position Y initiale
     */
    public Enemy(double x, double y) {
        super(x, y);
        this.random = new Random();

        // Vitesse verticale fixe
        this.speedY = GameConstants.ENEMY_SPEED_Y;

        // Vitesse horizontale aléatoire (-1 à 1) * vitesse max
        this.speedX = (random.nextDouble() * 2 - 1) * GameConstants.ENEMY_SPEED_X;
    }

    /**
     * Déplace l'ennemi selon sa vitesse
     */
    @Override
    public void move() {
        // Déplacer l'ennemi
        this.x += speedX;
        this.y += speedY;

        // Changer de direction si l'ennemi atteint les bords de l'écran
        // (en supposant une largeur d'écran de 800, à ajuster selon votre configuration)
        if (this.x <= 0 || this.x >= 800 - width) {
            speedX = -speedX;
        }

        // Changement aléatoire de direction (faible probabilité)
        if (random.nextDouble() < GameConstants.ENEMY_DIRECTION_CHANGE_CHANCE) {
            speedX = (random.nextDouble() * 2 - 1) * GameConstants.ENEMY_SPEED_X;
        }
    }

    /**
     * Obtient la vitesse horizontale
     * @return Vitesse X
     */
    public double getSpeedX() {
        return speedX;
    }

    /**
     * Définit la vitesse horizontale
     * @param speedX Nouvelle vitesse X
     */
    public void setSpeedX(double speedX) {
        this.speedX = speedX;
    }

    /**
     * Obtient la vitesse verticale
     * @return Vitesse Y
     */
    public double getSpeedY() {
        return speedY;
    }

    /**
     * Définit la vitesse verticale
     * @param speedY Nouvelle vitesse Y
     */
    public void setSpeedY(double speedY) {
        this.speedY = speedY;
    }
}
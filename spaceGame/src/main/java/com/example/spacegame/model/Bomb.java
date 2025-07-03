package com.example.spacegame.model;

import com.example.spacegame.utils.GameConstants;

/**
 * Classe représentant une bombe larguée par un ennemi
 */
public class Bomb extends GameObject {

    private double speed;

    /**
     * Constructeur de la bombe
     * @param x Position X initiale
     * @param y Position Y initiale
     */
    public Bomb(double x, double y) {
        super(x, y);
        this.speed = GameConstants.BOMB_SPEED;
    }

    /**
     * Déplace la bombe vers le bas
     */
    @Override
    public void move() {
        this.y += speed;
    }

    /**
     * Obtient la vitesse de la bombe
     * @return Vitesse
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Définit la vitesse de la bombe
     * @param speed Nouvelle vitesse
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
package com.example.spacegame.model;

import com.example.spacegame.utils.GameConstants;

/**
 * Classe représentant un projectile tiré par le joueur
 */
public class Projectile extends GameObject {

    private double speed;

    /**
     * Constructeur du projectile
     * @param x Position X initiale
     * @param y Position Y initiale
     */
    public Projectile(double x, double y) {
        super(x, y);
        this.speed = GameConstants.PROJECTILE_SPEED;
    }

    /**
     * Déplace le projectile vers le haut
     */
    @Override
    public void move() {
        this.y -= speed;
    }

    /**
     * Obtient la vitesse du projectile
     * @return Vitesse
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Définit la vitesse du projectile
     * @param speed Nouvelle vitesse
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
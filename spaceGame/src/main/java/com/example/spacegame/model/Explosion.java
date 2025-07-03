package com.example.spacegame.model;

/**
 * Classe représentant une explosion
 */
public class Explosion extends GameObject {

    private int duration;
    private int currentFrame;
    private boolean finished;

    /**
     * Constructeur de l'explosion
     * @param x Position X
     * @param y Position Y
     */
    public Explosion(double x, double y) {
        super(x, y);
        this.duration = 30; // Nombre de frames que dure l'explosion
        this.currentFrame = 0;
        this.finished = false;
    }

    /**
     * Met à jour l'état de l'explosion
     */
    public void update() {
        currentFrame++;

        if (currentFrame >= duration) {
            finished = true;
        }
    }

    /**
     * Vérifie si l'explosion est terminée
     * @return true si l'animation est terminée
     */
    public boolean isFinished() {
        return finished;
    }

    /**
     * L'explosion ne se déplace pas
     */
    @Override
    public void move() {
        // L'explosion ne se déplace pas
    }

    /**
     * Obtient le frame actuel
     * @return Frame actuel
     */
    public int getCurrentFrame() {
        return currentFrame;
    }

    /**
     * Définit le frame actuel
     * @param currentFrame Nouveau frame
     */
    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }

    /**
     * Obtient la durée de l'explosion
     * @return Durée en frames
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Définit la durée de l'explosion
     * @param duration Nouvelle durée en frames
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }
}
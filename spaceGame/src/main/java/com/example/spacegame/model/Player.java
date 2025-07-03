package com.example.spacegame.model;

public class Player extends GameObject {

    private int lives;
    private final double speed = 5.0;

    public Player(double x, double y) {
        super(x, y);  // Initialise x et y via GameObject
        this.lives = 3;
    }

    public void moveLeft() {
        x -= speed;
    }

    public void moveRight() {
        x += speed;
    }

    public void decreaseLives() {
        lives--;
    }

    @Override
    public void move() {

    }

    public Projectile shoot() {
        double x = getView().getLayoutX() + getView().getBoundsInParent().getWidth() / 2 - 2;
        double y = getView().getLayoutY();
        return new Projectile(x, y);
    }


    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }
}

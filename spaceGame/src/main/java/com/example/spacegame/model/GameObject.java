package com.example.spacegame.model;

import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;

/**
 * Classe abstraite représentant un objet de jeu générique avec position et dimensions
 */
public abstract class GameObject {

    protected double x;
    protected double y;
    protected double width;
    protected double height;
    protected ImageView view;




    /**
     * Constructeur avec position
     * @param x Position X initiale
     * @param y Position Y initiale
     */
    public GameObject(double x, double y) {
        this.x = x;
        this.y = y;
        this.width = 0;
        this.height = 0;
    }

    /**
     * Constructeur avec position et dimensions
     * @param x Position X initiale
     * @param y Position Y initiale
     * @param width Largeur de l'objet
     * @param height Hauteur de l'objet
     */
    public GameObject(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean intersects(GameObject other) {
        return this.getBounds().intersects(other.getBounds());
    }

    /**
     * Obtient la position X
     * @return Position X
     */
    public double getX() {
        return x;
    }

    /**
     * Définit la position X
     * @param x Nouvelle position X
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Obtient la position Y
     * @return Position Y
     */
    public double getY() {
        return y;
    }

    /**
     * Définit la position Y
     * @param y Nouvelle position Y
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Obtient la largeur
     * @return Largeur
     */
    public double getWidth() {
        return width;
    }

    /**
     * Définit la largeur
     * @param width Nouvelle largeur
     */
    public void setWidth(double width) {
        this.width = width;
    }

    /**
     * Obtient la hauteur
     * @return Hauteur
     */
    public double getHeight() {
        return height;
    }

    /**
     * Définit la hauteur
     * @param height Nouvelle hauteur
     */
    public void setHeight(double height) {
        this.height = height;
    }

    /**
     * Obtient la vue associée à l'objet
     * @return Vue ImageView
     */
    public ImageView getView() {
        return view;
    }

    /**
     * Définit la vue associée à l'objet
     * @param view Nouvelle vue ImageView
     */
    public void setView(ImageView view) {
        this.view = view;
        if (view != null) {
            this.width = view.getFitWidth();
            this.height = view.getFitHeight();
        }
    }
    public Bounds getBounds() {
        return view.getBoundsInParent();
    }


    /**
     * Méthode abstraite pour déplacer l'objet
     * Chaque sous-classe doit implémenter sa propre logique de déplacement
     */
    public abstract void move();
}
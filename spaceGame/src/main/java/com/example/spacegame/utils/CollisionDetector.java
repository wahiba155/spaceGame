package com.example.spacegame.utils;

import com.example.spacegame.model.Enemy;
import com.example.spacegame.model.GameObject;

/**
 * Classe utilitaire pour détecter les collisions entre objets de jeu
 */
public class CollisionDetector {

    /**
     * Vérifie s'il y a collision entre deux objets
     * Utilise l'algorithme de détection de collision par boîtes englobantes
     *
     * @param obj1 Premier objet
     * @param obj2 Deuxième objet
     * @return true s'il y a collision
     */
    public boolean checkCollision(GameObject obj1, GameObject obj2){
        // Obtenir les coordonnées des boîtes englobantes
        double left1 = obj1.getX();
        double top1 = obj1.getY();
        double right1 = obj1.getX() + obj1.getWidth();
        double bottom1 = obj1.getY() + obj1.getHeight();

        double left2 = obj2.getX();
        double top2 = obj2.getY();
        double right2 = obj2.getX() + obj2.getWidth();
        double bottom2 = obj2.getY() + obj2.getHeight();

        // Vérifier s'il n'y a PAS de collision (cas plus simples à déterminer)
        // Si un des cas est vrai, alors il n'y a pas de collision
        if (bottom1 < top2 || top1 > bottom2 || right1 < left2 || left1 > right2) {
            return false;
        }

        // Si aucun des cas ci-dessus n'est vrai, alors il y a collision
        return true;
    }

    /**
     * Vérifie si un objet est complètement sorti de l'écran
     *
     * @param obj Objet à vérifier
     * @param screenWidth Largeur de l'écran
     * @param screenHeight Hauteur de l'écran
     * @return true si l'objet est hors de l'écran
     */
    public boolean isOutOfScreen(GameObject obj, double screenWidth, double screenHeight) {
        return obj.getX() + obj.getWidth() < 0 ||
                obj.getX() > screenWidth ||
                obj.getY() + obj.getHeight() < 0 ||
                obj.getY() > screenHeight;
    }
}
package com.example.spacegame.utils;

/**
 * Classe contenant les constantes du jeu
 */
public class GameConstants {

    // Configuration joueur
    public static final int PLAYER_INITIAL_LIVES = 3;
    public static final double PLAYER_SPEED = 5.0;

    // Configuration projectile
    public static final double PROJECTILE_SPEED = 10.0;
    public static final long PROJECTILE_COOLDOWN = 300; // Délai entre tirs en millisecondes

    // Configuration ennemi
    public static final double ENEMY_SPEED_Y = 1.0;
    public static final double ENEMY_SPEED_X = 2.0;
    public static final double ENEMY_DIRECTION_CHANGE_CHANCE = 0.01; // 1% de chance par frame

    // Configuration bombe
    public static final double BOMB_SPEED = 3.0;
    public static final double BOMB_DROP_CHANCE = 0.005; // 0.5% de chance par frame

    // Configuration écran
    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 600;

    // Paramètres de jeu
    public static final int MAX_LEVEL = 3;

    private GameConstants() {
        // Constructeur privé pour empêcher l'instanciation
    }
}
package org.example.SMA;

public class GAUtils {
    public static final double ALPHA = 0.1;
    public static final double GAMMA = 0.9;
    public static final int NUM_STATES = 9;
    public static final int NUM_ACTIONS = 4;
    public static final int GRID_SIZE = 3;
    public static final int[][] GRID = {
            {1, 0, 0},
            {-1, 0, 0},
            {0, 0, 0}
    };
    public static final int[][] actions ={
        {0, -1}, // Gauche
        {0, 1},  // Droite
        {1, 0},  // Bas
        {-1, 0}  // Haut
    };
    public static final int MAX_EPOCH = 2000;
    public static final int NUM_AGENTS = 9; 
}

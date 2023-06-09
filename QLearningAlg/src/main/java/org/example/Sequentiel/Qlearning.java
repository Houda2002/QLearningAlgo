package org.example.Sequentiel;

import java.util.Random;

public class Qlearning {

    private final double ALPHA = 0.1;
    private final double GAMMA = 0.9;
    private final int MAX_EPOCH = 300;
    private final int GRID_SIZE = 3;
    private final int ACTION_SIZE = 4;
    private int[][] GRID;
    private double[][] qTable = new double[GRID_SIZE * GRID_SIZE][ACTION_SIZE];

    private int[][] actions;
    private int statei;
    private int statej;

    public Qlearning() {
        // Table d'actions possibles
        actions = new int[][]{
                {0, -1}, // Gauche
                {0, 1},  // Droite
                {1, 0},  // Bas
                {-1, 0}  // Haut
        };

        // Récompenses
        GRID = new int[][]{
                {1, 0, 0},
                {-1, 0, 0},
                {0, 0, 0}
        };
    }

    // Réinitialiser l'état à l'état initial après chaque épisode
    private void resetState() {
        statei = 2;
        statej = 0;
    }

    // Choisir une action
    private int chooseAction(double eps) {
        Random rn = new Random();
        double bestQ = 0;
        int act = 0;
        if (rn.nextDouble() < eps) {
            // Exploration
            act = rn.nextInt(ACTION_SIZE);
        } else {
            // Exploitation : choisir la meilleure action pour l'état actuel
            int st = statei * GRID_SIZE + statej;
            for (int i = 0; i < ACTION_SIZE; i++) {
                if (qTable[st][i] > bestQ) {
                    bestQ = qTable[st][i];
                    act = i;
                }
            }
        }
        return act;
    }

    private int executeAction(int act) {
        statei = Math.max(0, Math.min(actions[act][0] + statei, 2));
        statej = Math.max(0, Math.min(actions[act][1] + statej, 2));
        return statei * GRID_SIZE + statej;
    }

    private boolean finished() {
        return (GRID[statei][statej] == 1);
    }

    private void showResult() {
        System.out.println("***** Table Q *****");
        for (double[] line : qTable) {
            System.out.print("[");
            for (double qvalue : line) {
                System.out.print(qvalue + ", ");
            }
            System.out.println("]");
        }
        System.out.println(" ");
        resetState();
        while (!finished()) {
            int act = chooseAction(0);
            System.out.println("state : " + (statei * GRID_SIZE + statej) + ", Action : " + act);
            executeAction(act);
        }
        System.out.println("Final state : " + (statei * GRID_SIZE + statej) );

    }

    public void runQLearning() {
        int it = 0;
        resetState();
        int currentState;
        int nextState;
        int act, act1;
        while (it < MAX_EPOCH) {
            resetState();
            while (!finished()) {
                currentState = statei * GRID_SIZE + statej;
                act = chooseAction(0.4);
                nextState = executeAction(act);
                act1 = chooseAction(0);
                qTable[currentState][act] = qTable[currentState][act] + ALPHA * (GRID[statei][statej] + GAMMA * qTable[nextState][act1] - qTable[currentState][act]);
            }
            it++;
        }
        showResult();
    }

}

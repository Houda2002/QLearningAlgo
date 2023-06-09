package org.example.SMA;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.SequentialBehaviour;

import java.util.Random;

import static org.example.SMA.GAUtils.GRID_SIZE;
import static org.example.SMA.GAUtils.actions;
import static org.example.SMA.GAUtils.ALPHA;
import static org.example.SMA.GAUtils.GAMMA;
import static org.example.SMA.GAUtils.MAX_EPOCH;
import static org.example.SMA.GAUtils.NUM_ACTIONS;
import static org.example.SMA.GAUtils.NUM_AGENTS;

public class QLearningAlgorithme extends Agent {
    private double[][] qTable;
    private int statei;
    private int statej;

    @Override
    protected void setup() {
        qTable = new double[GRID_SIZE * GRID_SIZE][NUM_ACTIONS];

        SequentialBehaviour sequentialBehaviour = new SequentialBehaviour();
        sequentialBehaviour.addSubBehaviour(new QLearningBehaviour());
        addBehaviour(sequentialBehaviour);
    }

    private class QLearningBehaviour extends Behaviour {
        private boolean finished = false;

        @Override
        public void action() {
            runQLearning();
            showResult();
            finished = true;
        }

        @Override
        public boolean done() {
            return finished;
        }
    }

    public int chooseAction( double eps) {
        Random rn = new Random();
        double bestQ = 0;
        int act = 0;
        if (rn.nextDouble() < eps) {
            act = rn.nextInt(NUM_ACTIONS);
        } else {
            int st = statei * GRID_SIZE + statej;
            for (int i = 0; i < NUM_ACTIONS; i++) {
                if (qTable[st][i] > bestQ) {
                    bestQ = qTable[st][i];
                    act = i;
                }
            }
        }
        return act;
    }

    private int executeAction(int act) {
        statei = Math.max(0, Math.min(actions[act][0] + statei, GRID_SIZE - 1));
        statej = Math.max(0, Math.min(actions[act][1] + statej, GRID_SIZE - 1));

        return statei * GRID_SIZE + statej;
    }


    private boolean finished() {
        return (GAUtils.GRID[statei][statej]== 1);
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


    private void runQLearning() {
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
                qTable[currentState][act] = qTable[currentState][act] + ALPHA * (GAUtils.GRID[statei][statej] + GAMMA * qTable[nextState][act1] - qTable[currentState][act]);
            }
            it++;
        }

    }


    private void resetState() {
        statei = 2;
        statej = 0;
    }

}

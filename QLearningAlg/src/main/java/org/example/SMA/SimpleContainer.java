package org.example.SMA;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ControllerException;
public class SimpleContainer {
    public static void main(String[] args) {
        try {
            Runtime runtime = Runtime.instance();
            Profile profile = new ProfileImpl("localhost", 1098, null);
            AgentContainer agentContainer = runtime.createMainContainer(profile);
            AgentController agentLearning = agentContainer.createNewAgent("Agent", QLearningAlgorithme.class.getName(), new Object[]{});
            agentLearning.start();
        } catch (ControllerException e) {
            e.printStackTrace();
        }
    }
}

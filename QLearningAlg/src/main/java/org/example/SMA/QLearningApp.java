package org.example.SMA;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.ControllerException;


public class QLearningApp {
    public static void main(String[] args) throws ControllerException {

        Runtime runtime=Runtime.instance();
        Profile profile = new ProfileImpl(false);
        profile.setParameter(Profile.MAIN_HOST, "localhost");
        profile.setParameter(Profile.GUI, "true");
        AgentContainer container=runtime.createMainContainer(profile);

       container.start();
    }
}
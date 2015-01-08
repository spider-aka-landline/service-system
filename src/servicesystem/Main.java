package servicesystem;

import entities.DipoleData;
import experiments.generators.ExperimentsGenerator;
import experiments.generators.ExperimentsGeneratorWithStressEvent;
import servicesystem.events.AddNewBestProviderEvent;
import servicesystem.events.StressEvent;
import strategies.Strategy;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class Main {

    public static void main(String... args) throws IOException {
        int providersQuantity = 30;
        int[] usersQuantity = {300/*30000 , 300000 */};

        for (int users : usersQuantity) {
            //Dipole (users,providers). min,max.
            DipoleData minDipole = new DipoleData(users, providersQuantity);
            SystemsBruteForcer system = getSystemBruteForcer(minDipole, minDipole);
            system.run();
        }

    }

    private static SystemsBruteForcer getSystemBruteForcer(DipoleData min, DipoleData max) {
        boolean generateInitData = true; //currently works with generation only
        boolean isVariance = false; // with variable parameters of service

        int iterations = 300;
        int modellingTime = 100; //tasks quantity

        boolean stressTest = true; //run with Stress Event

        Map<String, Strategy> allStrategies = getStrategies();
        ExperimentsGenerator generator = getExperimentsGenerator(stressTest, allStrategies);
        return new SystemsBruteForcer(generateInitData, isVariance,
                min, max, iterations, modellingTime, generator);
    }

    private static ExperimentsGenerator getExperimentsGenerator(
            boolean stressTest, Map<String, Strategy> allStrategies) {
        if (stressTest) {
            StressEvent event = getStressEvent();
            return ExperimentsGeneratorWithStressEvent.getInstance(allStrategies, event);
        } else {
            return ExperimentsGenerator.getInstance(allStrategies);
        }
    }

    private static StressEvent getStressEvent() {
        StressEvent event;
        event = new AddNewBestProviderEvent(5);
        return event;
    }

    private static Map<String, Strategy> getStrategies() {
        Map<String, Strategy> strategies = new TreeMap<>();

        //First experiment: random
        //strategies.put("random", new strategies.newpackage.RandomStrategy());

        //Second experiment: RL, e-decreasing
        //strategies.put("rl", new strategies.newpackage.SimpleRLStrategy());

        //Third experiment: RL, e-decreasing, reputation
        strategies.put("reputationV", new strategies.newpackage.ReputationStrategy());

        //4th experiment: reputation only (max reputation)
        //strategies.put("reputationR", new strategies.newpackage.ReputationRStrategy());

        return strategies;
    }
}

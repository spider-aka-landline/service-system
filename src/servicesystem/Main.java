package servicesystem;

import entities.DipoleData;
import experiments.generators.ExperimentsGenerator;
import experiments.generators.ExperimentsGeneratorWithStressEvent;
import servicesystem.bruteforcer.SystemsBruteForcer;
import servicesystem.events.FailBestProviderEvent;
import servicesystem.events.StressEvent;
import strategies.Strategy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public class Main {

    public static void main(String... args) throws IOException {
        int[] providersQuantity = {10,30/*, 300*/};
        int[] usersQuantity = {3000, 30000/*30000, 300000*/};
        Collection<DipoleData> dipoles = getDipoles(usersQuantity, providersQuantity);

        SystemsBruteForcer system = getSystemBruteForcer(dipoles);
        system.run();
    }



    private static Collection<DipoleData> getDipoles(int[] usersQuantity, int[] providersQuantity) {
        Collection<DipoleData> dipoles = new ArrayList<>();
        for (int users : usersQuantity) {
            for (int providers : providersQuantity) {
                //Dipole (users,providers). min,max.
                dipoles.add(new DipoleData(users, providers));
            }
        }
        return dipoles;
    }

    private static SystemsBruteForcer getSystemBruteForcer(Collection<DipoleData> dipoles) {
        boolean generateInitData = false; // generate input data or read them
        boolean isVariance = false; // with variable parameters of service

        int iterations = 1000;
        int modellingTime = 300; //tasks quantity

        boolean stressTest = true; //run with Stress Event

        Map<String, Strategy> allStrategies = getStrategies();
        ExperimentsGenerator generator = getExperimentsGenerator(stressTest, allStrategies);
        return new SystemsBruteForcer(generateInitData, isVariance,
                dipoles, iterations, modellingTime, generator);
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
        event = new FailBestProviderEvent(50, 65);
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

package experiments.generators;

import entities.providers.ServiceProvider;
import entities.users.User;
import experiments.Experiment;
import experiments.ExperimentData;
import experiments.ExperimentDataWithVariance;
import experiments.SimpleExperiment;
import experiments.SimpleExperimentWithStressEvent;
import exploration.EpsilonDecreasingStrategy;
import exploration.ExplorationStrategy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import servicesystem.events.AddNewBestProviderEvent;
import servicesystem.events.StressEvent;
import strategies.oldpackage.RLStrategy;
import strategies.oldpackage.RLWithReputationMaxReputationStrategy;
import strategies.oldpackage.RLWithReputationStrategy;
import strategies.Strategy;

public class ExperimentsGenerator {

    private static ExperimentsGenerator instance;

    protected long experimentsCounter = 0;

    protected ExperimentsGenerator() {
    }

    public static synchronized ExperimentsGenerator getInstance() {
        if (instance == null) {
            instance = new ExperimentsGenerator();
        }
        return instance;
    }

    public Experiment createSimpleExperiment(String name,
            String currentExperimentBlockName, ExplorationStrategy strategy,
            Strategy str, ExperimentData input) {

        String path = makePath(name, currentExperimentBlockName);
        Experiment newbornExperiment
                = new SimpleExperiment(experimentsCounter,
                        path, strategy, str, input);

        return newbornExperiment;
    }

    public Experiment createSimpleExperiment(String name,
            String currentExperimentBlockName, ExplorationStrategy strategy,
            Strategy str, ExperimentData input, StressEvent event) {

        String path = makePath(name, currentExperimentBlockName);
        Experiment newbornExperiment
                = new SimpleExperimentWithStressEvent(experimentsCounter,
                        path, strategy, str, input, event);

        return newbornExperiment;
    }

    private String makePath(String experimentName, String experimentBlockName) {
        StringBuilder path = new StringBuilder();
        path.append(experimentBlockName).append("/").append(experimentName);
        return path.toString();
    }

    private String generateExperimentBlockName(Boolean generateWithVar) {
        if (generateWithVar) {
            return "const-with-variances";
        } else {
            return "simple-constants";
        }
    }

    private ExperimentData generateExperimentData(Collection<User> currentUsers,
            Collection<ServiceProvider> currentProviders,
            Integer tasksNumber, Integer iterationsNumber,
            Boolean withVariance) {
        ExperimentData generatedData;
        if (withVariance) {
            generatedData = new ExperimentDataWithVariance(currentUsers,
                    currentProviders, tasksNumber, iterationsNumber);
        } else {
            generatedData = new ExperimentData(currentUsers,
                    currentProviders, tasksNumber, iterationsNumber);
        }
        return generatedData;
    }

    public List<Experiment>
            createExperimentPlan(Collection<User> currentUsers,
                    Collection<ServiceProvider> currentProviders,
                    Integer tasksNumber, Integer iterationsNumber,
                    Boolean generateWithVar) {

        //Only one exploration strategy;
        ExplorationStrategy strategy = new EpsilonDecreasingStrategy();

        //container for all experiments
        List<Experiment> experiments = new ArrayList<>();

        ExperimentData expData = generateExperimentData(currentUsers,
                currentProviders, tasksNumber, iterationsNumber, generateWithVar);

        String currentExperimentBlockName
                = generateExperimentBlockName(generateWithVar);

        StressEvent addedProviderEvent = new AddNewBestProviderEvent(6);
        Map<String, Strategy> allStrategies = getStrategies();
        for (Entry<String, Strategy> entry : allStrategies.entrySet()) {
            Experiment exp = createSimpleExperiment(entry.getKey(),
                    currentExperimentBlockName, strategy,
                    entry.getValue(), expData, addedProviderEvent);
            experiments.add(exp);
        }

        return experiments;
    }

    private Map<String, Strategy> getStrategies() {
        Map<String, Strategy> strategies = new TreeMap<>();

        //First experiment: random
        strategies.put("random", new strategies.oldpackage.RandomStrategy());
        strategies.put("randomNew", new strategies.newpackage.RandomStrategy());

        //Second experiment: RL, e-decreasing
        strategies.put("rl", new strategies.oldpackage.RLStrategy());
        strategies.put("rlNew", new strategies.newpackage.SimpleRLStrategy());

        //Third experiment: RL, e-decreasing, reputation
        strategies.put("reputationV", new strategies.oldpackage.RLWithReputationStrategy());
        strategies.put("reputationVNew", new strategies.newpackage.ReputationStrategy());

        //4th experiment: reputation only (max reputation)
        strategies.put("reputationR", new strategies.oldpackage.RLWithReputationMaxReputationStrategy());
        strategies.put("reputationR", new strategies.newpackage.ReputationRStrategy());

        return strategies;
    }
}

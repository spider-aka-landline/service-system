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
import servicesystem.events.StressEvent;
import strategies.RLStrategy;
import strategies.RLWithReputationMaxReputationStrategy;
import strategies.RLWithReputationStrategy;
import strategies.RandomStrategy;
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
        List<Experiment> exps = new ArrayList<>();

        ExperimentData expData = generateExperimentData(currentUsers,
                currentProviders, tasksNumber, iterationsNumber, generateWithVar);

        String currentExperimentBlockName
                = generateExperimentBlockName(generateWithVar);

        Map<String, Strategy> allStrategies = getStrategies();
        for (Entry<String, Strategy> entry : allStrategies.entrySet()) {
            Experiment exp = createSimpleExperiment(entry.getKey(),
                currentExperimentBlockName, strategy,
                entry.getValue(), expData);
            exps.add(exp);
        }

        return exps;
    }

    private Map<String, Strategy> getStrategies() {
        Map<String, Strategy> strategies = new TreeMap<>();
        //First experiment: random
        strategies.put("random", new RandomStrategy());
        //Second experiment: RL, e-decreasing
        strategies.put("rl", new RLStrategy());
        //Third experiment: RL, e-decreasing, reputation
        strategies.put("reputationV", new RLWithReputationStrategy());
        //4th experiment: reputation only (max reputation)
        strategies.put("reputationR",
                new RLWithReputationMaxReputationStrategy());
        return strategies;
    }
}

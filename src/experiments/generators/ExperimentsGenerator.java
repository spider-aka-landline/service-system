package experiments.generators;

import entities.providers.ServiceProvider;
import entities.users.User;
import experiments.*;
import exploration.EpsilonDecreasingStrategy;
import exploration.ExplorationStrategy;
import servicesystem.events.AddNewBestProviderEvent;
import servicesystem.events.StressEvent;
import strategies.Strategy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ExperimentsGenerator {

    private static ExperimentsGenerator instance;

    protected long experimentsCounter = 0;
    protected Map<String, Strategy> allStrategies;

    protected ExperimentsGenerator(Map<String, Strategy> strategies) {
        allStrategies = strategies;
    }

    public static synchronized ExperimentsGenerator getInstance(Map<String, Strategy> strategies) {
        if (instance == null) {
            instance = new ExperimentsGenerator(strategies);
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
        for (Entry<String, Strategy> entry : allStrategies.entrySet()) {
            Experiment exp = createSimpleExperiment(entry.getKey(),
                    currentExperimentBlockName, strategy,
                    entry.getValue(), expData, addedProviderEvent);
            experiments.add(exp);
        }

        return experiments;
    }


}

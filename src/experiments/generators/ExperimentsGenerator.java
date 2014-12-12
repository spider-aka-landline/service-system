package experiments.generators;

import entities.providers.ServiceProvider;
import entities.users.User;
import experiments.Experiment;
import experiments.ExperimentData;
import experiments.ExperimentDataWithVariance;
import experiments.SimpleExperiment;
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

    private long experimentsCounter = 0;
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

    protected Experiment createSimpleExperiment(String name,
                                             String currentExperimentBlockName, ExplorationStrategy strategy,
                                             Strategy str, ExperimentData input) {

        String path = makePath(name, currentExperimentBlockName);
        Experiment newbornExperiment
                = getExperiment(experimentsCounter, path, strategy, str, input);

        return newbornExperiment;
    }

    protected Experiment getExperiment(Long counter, String path, ExplorationStrategy strategy,
                                       Strategy str, ExperimentData input) {
        return
                new SimpleExperiment(experimentsCounter, path, strategy, str, input);
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


        for (Entry<String, Strategy> entry : allStrategies.entrySet()) {
            Experiment exp = createSimpleExperiment(entry.getKey(),
                    currentExperimentBlockName, strategy,
                    entry.getValue(), expData);
            experiments.add(exp);
        }

        return experiments;
    }


}

package experiments.generators;

import experiments.Experiment;
import experiments.ExperimentData;
import experiments.SimpleExperimentWithStressEvent;
import exploration.ExplorationStrategy;
import servicesystem.events.StressEvent;
import strategies.Strategy;

import java.util.Map;

public class ExperimentsGeneratorWithStressEvent extends ExperimentsGenerator {

    StressEvent event;
    private static ExperimentsGeneratorWithStressEvent instance;

    private long experimentsCounter = 0;

    private ExperimentsGeneratorWithStressEvent(Map<String, Strategy> strategies, StressEvent stressEvent) {
        super(strategies);
        event = stressEvent;
    }

    public static synchronized ExperimentsGeneratorWithStressEvent getInstance(
            Map<String, Strategy> strategies, StressEvent event) {
        if (instance == null) {
            instance = new ExperimentsGeneratorWithStressEvent(strategies, event);
        }
        return instance;
    }

    protected Experiment getExperiment(Long counter, String path, ExplorationStrategy strategy,
                                       Strategy str, ExperimentData input) {
        return new SimpleExperimentWithStressEvent(experimentsCounter,
                path, strategy, str, input, event);
    }

}

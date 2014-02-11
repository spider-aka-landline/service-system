package servicesystem;

import experiments.Experiment;
import experiments.ExperimentData;
import experiments.SimpleExperiment;
import exploration.EpsilonDecreasingStrategy;
import exploration.ExplorationStrategy;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static final Integer ITERATIONS_NUMBER = 1;
    public static final Integer USERS_NUMBER = 2;
    public static final Integer PROVIDERS_NUMBER = 4;
    public static final Integer TASKS_NUMBER = 50;

    static Boolean generateAll = true; //didn't work for false now

    public static void main(String... args) throws FileNotFoundException {

        //Only one exploration strategy;
        ExplorationStrategy strategy = new EpsilonDecreasingStrategy();

        //container for all experiments
        List<Experiment> exps = new ArrayList<>();

        //Generated data for all experiments
        ExperimentData generated = new ExperimentData(USERS_NUMBER,
                PROVIDERS_NUMBER, TASKS_NUMBER, ITERATIONS_NUMBER);
        
        //First experiment: RL, e-decreasing,
        Experiment exp1 = new SimpleExperiment(Long.valueOf(1),
                "simple-constants", strategy, generated);

        exps.add(exp1);

        ExperimentsRunner runner = new ExperimentsRunner(exps);
        runner.run();
    }

}

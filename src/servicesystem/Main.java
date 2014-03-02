package servicesystem;

import experiments.Experiment;
import experiments.ExperimentData;
import experiments.SimpleExperiment;
import exploration.EpsilonDecreasingStrategy;
import exploration.ExplorationStrategy;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import reputationsystem.ChooseProviderStrategy;

public class Main {

    public static final Integer ITERATIONS_NUMBER = 100;
    public static final Integer USERS_NUMBER = 5;
    public static final Integer PROVIDERS_NUMBER = 10;
    public static final Integer TASKS_NUMBER = 5000;

    static Boolean generateAll = true; //didn't work for false now

    public static void main(String... args) throws FileNotFoundException {

        //Only one exploration strategy;
        ExplorationStrategy strategy = new EpsilonDecreasingStrategy();

        //container for all experiments
        List<Experiment> exps = new ArrayList<>();

        //Generated data for all experiments
        ExperimentData generated = new ExperimentData(USERS_NUMBER,
                PROVIDERS_NUMBER, TASKS_NUMBER, ITERATIONS_NUMBER);
        
        //First experiment: random
        Experiment exp01 = new SimpleExperiment(Long.valueOf(1),
                "simple-constants/random", strategy, 
                ChooseProviderStrategy.RANDOM, generated);
        
        exps.add(exp01);
        
        //Second experiment: RL, e-decreasing
        Experiment exp02 = new SimpleExperiment(Long.valueOf(2),
                "simple-constants/rl", strategy, 
                ChooseProviderStrategy.RL, generated);
        
        exps.add(exp02);
        
        //Third experiment: RL, e-decreasing, reputation
        Experiment exp03 = new SimpleExperiment(Long.valueOf(3),
                "simple-constants/reputation", strategy, 
                ChooseProviderStrategy.RLWITHREPUTATION, generated);
        
        exps.add(exp03);

        ExperimentsRunner runner = new ExperimentsRunner(exps);
        runner.run();
    }

}

package servicesystem;

import experiments.Experiment;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.PriorityQueue;
import java.util.Queue;

public final class ExperimentsRunner {

    private final Queue<Experiment> experiments = new PriorityQueue<>();
    private static Experiment currentExperiment;
    
    public ExperimentsRunner(Collection<Experiment> all) {
        all.forEach(b -> experiments.add(b));
    }

    public static void logExperimentData(Double value) {
        currentExperiment.logExperimentData(value);
    }

    public void run() throws FileNotFoundException {
        for (Experiment exp : experiments){
            currentExperiment = exp;
            currentExperiment.logInputData();
            currentExperiment.run();
            currentExperiment.printTotalResult();
        }

    }

}

package servicesystem;

import experiments.Experiment;
import java.util.Collection;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Queue;

public final class ExperimentsRunner {

    private final Queue<Experiment> experiments = new PriorityQueue<>();
    private final Iterator experimentsIterator = experiments.iterator();
    
    private static Experiment currentExperiment;
    
    public ExperimentsRunner(Collection<Experiment> all) {
        all.forEach(b -> experiments.add(b));
    }

    public static void logExperimentData(Double value) {
        currentExperiment.logExperimentData(value);
    }

    public void run() {
        while (experimentsIterator.hasNext()) {
            currentExperiment = (Experiment) experimentsIterator.next();
            currentExperiment.run();
            currentExperiment.printTotalResult();
        }

    }

}

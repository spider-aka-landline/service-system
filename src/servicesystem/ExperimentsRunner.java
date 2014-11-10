package servicesystem;

import experiments.Experiment;
import java.io.IOException;
import java.util.Collection;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import messages.ProviderResponse;

public final class ExperimentsRunner {

    private final Queue<Experiment> experiments;

    private static Experiment currentExperiment;

    public ExperimentsRunner(Collection<Experiment> all) {
        experiments = new PriorityQueue<>();
        experiments.addAll(all);
    }

    public static void logExperimentData(ProviderResponse pr, Double value) {
        currentExperiment.logExperimentData(pr, value);
    }

    public static void logExperimentData(long criteriaCompletionTime) {
        currentExperiment.logExperimentData(criteriaCompletionTime);
    }

    public void run() {

        for (Experiment exp : experiments) {
            try {
                currentExperiment = exp;
                currentExperiment.logInputData();
                currentExperiment.run();
                currentExperiment.printTotalResult();

            } catch (IOException ex) {
                Logger.getLogger(ExperimentsRunner.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}

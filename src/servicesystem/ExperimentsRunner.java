package servicesystem;

import experiments.Experiment;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import messages.ProviderResponse;

public final class ExperimentsRunner {

    private final Queue<Experiment> experiments = new PriorityQueue<>();
    private static Experiment currentExperiment;

    public ExperimentsRunner(Collection<Experiment> all) {
        all.forEach(b -> experiments.add(b));
    }

    public static void logExperimentData(ProviderResponse pr, Double value) {
        currentExperiment.logExperimentData(pr, value);
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

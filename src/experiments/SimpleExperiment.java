package experiments;

import exploration.ExplorationStrategy;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import servicesystem.ServiceSystem;

public class SimpleExperiment extends Experiment {

    public SimpleExperiment(Long i, String name, ExplorationStrategy strategy,
            ExperimentData input) {
        super(i, name, strategy, input);
    }

    @Override
    public void run() {
        //print input data
        try {
            logInputData();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SimpleExperiment.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //create servicesystem instance
        ServiceSystem system;
        for (int i = 0; i < data.getIterationsNumber(); i++) {
            system = new ServiceSystem(data, explorationStrategy);
            system.run();
            nextIteration();
        }

        //print output data
        try {
            printTotalResult();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SimpleExperiment.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

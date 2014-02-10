package experiments;

import exploration.ExplorationStrategy;
import servicesystem.ServiceSystem;

public class SimpleExperiment extends Experiment {

    public SimpleExperiment(Long i, String name, ExplorationStrategy strategy,
            ExperimentData input) {
        super(i, name, strategy, input);
    }

    @Override 
    protected ServiceSystem getServiceSystemInstance(){
        return new ServiceSystem(data, explorationStrategy);
    }

}

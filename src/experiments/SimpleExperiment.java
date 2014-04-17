package experiments;

import exploration.ExplorationStrategy;
import servicesystem.ServiceSystem;
import strategies.Strategy;

public class SimpleExperiment extends Experiment {

    Strategy experimentStrategy;
            
    public SimpleExperiment(Long i, String name, ExplorationStrategy strategy,
            Strategy str, ExperimentData input) {
        super(i, name, strategy, input);
        experimentStrategy = str;
    }

    @Override 
    protected ServiceSystem getServiceSystemInstance(){
        return new ServiceSystem(data, explorationStrategy, experimentStrategy);
    }

}

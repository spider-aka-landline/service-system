package experiments;

import exploration.ExplorationStrategy;
import reputationsystem.ChooseProviderStrategy;
import servicesystem.ServiceSystem;

public class SimpleExperiment extends Experiment {

    ChooseProviderStrategy experimentStrategy;
            
    public SimpleExperiment(Long i, String name, ExplorationStrategy strategy,
            ChooseProviderStrategy str, ExperimentData input) {
        super(i, name, strategy, input);
        experimentStrategy = str;
    }

    @Override 
    protected ServiceSystem getServiceSystemInstance(){
        return new ServiceSystem(data, explorationStrategy, experimentStrategy);
    }

}

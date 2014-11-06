package experiments;

import exploration.ExplorationStrategy;
import servicesystem.ServiceSystem;
import servicesystem.ServiceSystemWithStressEvent;
import servicesystem.events.StressEvent;
import strategies.Strategy;

public class SimpleExperimentWithStressEvent extends SimpleExperiment {

    StressEvent experimentEvent;
    
    public SimpleExperimentWithStressEvent(Long i, String name,
            ExplorationStrategy strategy, Strategy str, ExperimentData input,
            StressEvent event) {
        super(i, name, strategy, str, input);
        experimentEvent = event;
    }

    @Override
    protected ServiceSystem getServiceSystemInstance() {
        return new ServiceSystemWithStressEvent(data,
                explorationStrategy, experimentStrategy, experimentEvent);
    }

}

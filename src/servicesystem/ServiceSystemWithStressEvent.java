package servicesystem;

import experiments.ExperimentData;
import exploration.ExplorationStrategy;
import java.util.Collection;
import servicesystem.events.StressEvent;
import strategies.Strategy;
import validator.DifferenceValidator;

public class ServiceSystemWithStressEvent extends ServiceSystem {

    StressEvent event;
    
    public ServiceSystemWithStressEvent(ExperimentData data,
            ExplorationStrategy explorationStrategy, Strategy str,
            Collection<DifferenceValidator> c, StressEvent stress) {
        super(data, explorationStrategy, str, c);

    }

    public ServiceSystemWithStressEvent(ExperimentData data,
            ExplorationStrategy explorationStrategy, Strategy str,
            StressEvent stress) {
        super(data, explorationStrategy, str);

    }

    @Override
    public void run() {
        checked = false;
        while (!tasks.isEmpty()) {
            processCurrentRequest(tasks.poll());
            servedTasksNumber++;
            
            checkStressTrigger();
        }
    }

    private void checkStressTrigger() {
        if (event.isTriggerTime(servedTasksNumber))
            event.executeEvent(this);
    }

}

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
        while (state.hasTasks()) {
            processCurrentRequest(state.pollTask());
            state.incrementServedTasksNumber();

            checkStressTrigger();
        }
    }

    private void checkStressTrigger() {
        long currentTime = state.getServedTasksNumber();
        if (event.isTriggerTime(currentTime)) {
            event.executeEvent(state);
        } else if (event.isReadyToCheck(currentTime)) {
            event.checkCriteria(state);
        }
    }

}

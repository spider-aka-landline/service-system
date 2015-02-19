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
            Collection<DifferenceValidator> c, StressEvent stressEvent) {
        super(data, explorationStrategy, str, c);
        event = stressEvent;
    }

    public ServiceSystemWithStressEvent(ExperimentData data,
            ExplorationStrategy explorationStrategy, Strategy str,
            StressEvent stressEvent) {
        super(data, explorationStrategy, str);
        event = stressEvent;
    }

    @Override
    public void run() {
        checked = false;
        while (state.hasTasks()) {
            processCurrentRequest(state.pollTask());
            state.incrementServedTasksNumber();

            checkStressTrigger();
        }
        resetEventState();
    }

    private void resetEventState() {
        event.resetState(state);
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

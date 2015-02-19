package servicesystem.events;

import entities.Params;
import entities.providers.ServiceProvider;
import io.IO;
import servicesystem.ExperimentsRunner;
import servicesystem.ServiceSystemState;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

abstract class ChangeProviderEvent implements StressEvent {

    private final List<Long> triggerTime = new ArrayList<>();
    private int triggersCounter = 0;

    protected Params oldParams;
    protected Params changedParams;
    protected ServiceProvider changingProvider = null;
    private Boolean wereChecked = false;
    private Boolean isEventActive = false;


    public ChangeProviderEvent(long startTime) {
        this(startTime, Long.MAX_VALUE);
    }

    public ChangeProviderEvent(long startTime, long endTime) {
        triggerTime.add(startTime);
        triggerTime.add(endTime);
    }

    public ChangeProviderEvent(Collection<Long> timeIntervals) {
        checkTimeIntervals(timeIntervals);
        triggerTime.addAll(timeIntervals);
    }

    private final void checkTimeIntervals(Collection<Long> timeIntervals)
            throws IllegalArgumentException, NullPointerException {
        if (timeIntervals == null) throw new NullPointerException("Null instead of collection");
        if (timeIntervals.isEmpty()) throw new IllegalArgumentException("Empty collection");
        if (timeIntervals.size() % 2 != 0) throw new IllegalArgumentException("Wrong parity");

        List<Integer> negativePositions = new ArrayList<>();
        int positionCounter = 0;
        Long previousItem = Long.MIN_VALUE;
        for (Long item : timeIntervals) {
            if (item < 0) negativePositions.add(positionCounter);
            if (item <= previousItem) throw new IllegalArgumentException("Wrong interval found");
            previousItem = item;
            positionCounter++;
        }
        if (negativePositions.size() > 1) throw new IllegalArgumentException("Two or more negatives found");
        else if ((negativePositions.size() == 1) && (!negativePositions.get(0).equals(timeIntervals.size() - 1)))
            throw new IllegalArgumentException("Negative is not at the end of collection");


    }


    @Override
    public void executeEvent(ServiceSystemState state) {
        //this will be all plan

        if (changingProvider == null) {
            changingProvider = getChangingProvider(state);
            oldParams = changingProvider.getProperties();
        }

        if (isEventActive) endEvent(state);
        else startEvent(state);

        logChangingProvider(state);
        triggersCounter++;
    }

    abstract protected ServiceProvider getChangingProvider(ServiceSystemState state);

    abstract protected Params getChangedParams(ServiceSystemState state);

    private void startEvent(ServiceSystemState state) {
        if (changedParams == null)
            changedParams = getChangedParams(state);
        changeProviderSettings(state, changingProvider, changedParams);
        isEventActive = true;
    }

    public void resetState(ServiceSystemState state){
        endEvent(state);
        resetEvent();
    }

    private void resetEvent() {
        oldParams = null;
        changedParams = null;
        changingProvider = null;
        wereChecked = false;
        triggersCounter = 0;
    }

    private void endEvent(ServiceSystemState state) {
        changeProviderSettings(state, changingProvider, oldParams);
        isEventActive = false;
    }

    private void changeProviderSettings(ServiceSystemState state,
                                        ServiceProvider changingProvider, Params changedParams) {
        state.changeProviderParams(changingProvider, changedParams);
    }

    private Long getLastTriggerTime() {
        if (triggersCounter == 0)
            return 0L;

        return triggerTime.get(triggersCounter - 1);
    }

    private Long getCurrentTriggerTime() {
        if (triggersCounter >= triggerTime.size()) return Long.MAX_VALUE;
        Long resultTime = triggerTime.get(triggersCounter);
        if (resultTime < 0) {

            return Long.MAX_VALUE;
        }
        return resultTime;
    }

    @Override
    public boolean isTriggerTime(Long currentTime) {
        return (currentTime.equals(getCurrentTriggerTime()));
    }

    @Override
    public boolean isReadyToCheck(Long currentTime) {
        return (isEventActive && (currentTime > getLastTriggerTime()) && !wereChecked);
    }

    private void logStateToFile(ServiceSystemState state) {
        IO.logServiceSystemStateToFile(state);
    }

    @Override
    public void checkCriteria(ServiceSystemState state) {
        if (isCriteriaSatisfied(state)) {
            //поднять флаг
            wereChecked = true;
            isEventActive = true;
            //засечь время
            long time = state.getServedTasksNumber() - getLastTriggerTime();

           /*сделать что-то с матрицами для вывода/подсчета результатов
            *точно так же, как с остальными числами
            */
            logCriteriaCompletionTime(time);
        }
    }

    abstract protected boolean isCriteriaSatisfied(ServiceSystemState state);

    private void logCriteriaCompletionTime(long time) {
        ExperimentsRunner.logExperimentData(time);
    }

    private void logChangingProvider(ServiceSystemState state) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Provider (ID=").append(changingProvider.getID());
        stringBuilder.append(") were changed ( EventActive = ");
        stringBuilder.append(isEventActive);
        stringBuilder.append(")");

        IO.logChangingProvider(stringBuilder.toString());
    }

}
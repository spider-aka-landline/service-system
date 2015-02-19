package servicesystem.events;

import servicesystem.ServiceSystemState;

public interface  StressEvent {
   
    void executeEvent(ServiceSystemState state);
    boolean isTriggerTime(Long currentTime);
    boolean isReadyToCheck(Long currentTime);
    void checkCriteria(ServiceSystemState state);

    void resetState(ServiceSystemState state);
}

package servicesystem.events;

import servicesystem.ServiceSystemState;

public interface  StressEvent {
   
    void executeEvent(ServiceSystemState state);
    boolean isTriggerTime(long currentTime);
    boolean isReadyToCheck(long currentTime);
    void checkCriteria(ServiceSystemState state);

}

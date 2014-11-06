package servicesystem.events;

import servicesystem.ServiceSystem;

public interface  StressEvent {
   
    void executeEvent(ServiceSystem system);
    boolean isTriggerTime(long currentTime);

}

package messages;

import entities.Params;
import entities.Task;

public class StatisticEntry {

    Integer taskTime;
    Long userId;
    Long providerId;
    Params serviceProperties;
    Double userEstimate;

    public StatisticEntry(ProviderResponse pr, double value) {
        Task tempt = pr.getServedTask();
        taskTime = tempt.getCreationTime();
        userId = tempt.getSender().getID();
        providerId = pr.serviceProviderId;
        serviceProperties = pr.getServiceParameters();
        userEstimate = value;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder(String.valueOf(taskTime));
        s.append(" ").append(userId);
        s.append(" ").append(providerId);
        s.append(" ").append(serviceProperties);
        s.append(" ").append(userEstimate);
        return s.toString();
    }
}

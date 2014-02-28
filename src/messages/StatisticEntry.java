package messages;

import entities.Params;
import entities.Task;

public class StatisticEntry {

    private final Integer taskTime;
    private final Long userId;
    private final Integer providerId;
    private final Params serviceProperties;
    private final Double userEstimate;

    public StatisticEntry(ProviderResponse pr, double userEstimate) {
        Task tempt = pr.getServedTask();
        taskTime = tempt.getCreationTime();
        userId = tempt.getSender().getID();
        providerId = pr.getServiceProviderId();
        serviceProperties = pr.getServiceParameters();
        this.userEstimate = userEstimate;
    }

    public Double getUserEstimate(){
        return userEstimate;
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

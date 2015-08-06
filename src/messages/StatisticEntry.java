package messages;

import entities.ID;
import entities.Params;
import entities.Task;

public class StatisticEntry {

    private final Integer iterationCycle;
    private final Integer taskNumber;

    private final Integer taskTime;
    private final ID userId;
    private final ID providerId;
    private final Params serviceProperties;
    private final Double userEstimate;

    public StatisticEntry(Integer currentIterationCycle, Integer taskNumber, ProviderResponse pr, double userEstimate) {
        iterationCycle = currentIterationCycle;
        this.taskNumber = taskNumber;
        Task tempt = pr.getServedTask();
        taskTime = tempt.getCreationTime();
        userId = tempt.getSender().getID();
        providerId = pr.getServiceProviderId();
        serviceProperties = pr.getServiceParameters();
        this.userEstimate = userEstimate;
    }

    public static String getHeader(){
        return getHeader(",");
    }

    public static String getHeader(String delimiter) {
        StringBuilder s = new StringBuilder();
        s.append("iteration")
                .append(delimiter).append("taskNumber")
                .append(delimiter).append("userID")
                .append(delimiter).append("providerID")
                .append(delimiter).append("serviceProperties")
                .append(delimiter).append("userEstimate");
        return s.toString();
    }

    @Override
    public String toString() {
        return toString(",");
    }

    public String toString(String delimiter) {
        StringBuilder s = new StringBuilder();
        s.append(iterationCycle)
                .append(delimiter).append(taskNumber)
                .append(delimiter).append(userId)
                .append(delimiter).append(providerId)
                .append(delimiter).append(serviceProperties)
                .append(delimiter).append(userEstimate);
        return s.toString();
    }
}

package messages;

import entities.ID;
import entities.providers.ServiceProvider;
import reputationsystem.DataEntity;

import java.util.Map;

public class StateStatisticEntry {

    private final Integer iterationCycle;
    private final Integer taskNumber;

    private final ID providerId;
    private final Double expectation;
    private final Double reputation;

    public StateStatisticEntry(Integer iterationCycle, Integer taskNumber, Map.Entry<ServiceProvider, DataEntity> serviceProviderDataEntityMap) {
        this.iterationCycle = iterationCycle;
        this.taskNumber = taskNumber;

        ServiceProvider provider = serviceProviderDataEntityMap.getKey();
        DataEntity data = serviceProviderDataEntityMap.getValue();
        providerId = provider.getID();
        expectation = data.getExpectation();
        reputation = data.getReputation();
    }

    public static String getHeader(){
        return getHeader(",");
    }

    public static String getHeader(String delimiter) {
        StringBuilder s = new StringBuilder();
        s.append("iteration")
                .append(delimiter).append("taskNumber")
                .append(delimiter).append("providerID")
                .append(delimiter).append("expectation")
                .append(delimiter).append("reputation");
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
                .append(delimiter).append(providerId)
                .append(delimiter).append(expectation)
                .append(delimiter).append(reputation);
        return s.toString();
    }
}

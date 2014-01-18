package messages;

import entities.Params;
import entities.Task;

public class ProviderResponse {

    long serviceProviderId;
    private Task servedTask;
    private Params serviceParameters;

    public ProviderResponse(long id, Task t, Params par) {
        serviceProviderId = id;
        servedTask = t;
        serviceParameters = par;
    }

    public Params getServiceParameters() {
        return serviceParameters;
    }

    public long getServiceProviderId() {
        return serviceProviderId;
    }

    public Task getServedTask() {
        return servedTask;
    }

}

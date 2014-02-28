package messages;

import entities.Params;
import entities.Task;

public class ProviderResponse {

    int serviceProviderId;
    private Task servedTask;
    private Params serviceParameters;

    public ProviderResponse(int id, Task t, Params par) {
        serviceProviderId = id;
        servedTask = t;
        serviceParameters = par;
    }

    public Params getServiceParameters() {
        return serviceParameters;
    }

    public int getServiceProviderId() {
        return serviceProviderId;
    }

    public Task getServedTask() {
        return servedTask;
    }

}

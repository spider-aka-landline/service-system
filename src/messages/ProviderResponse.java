package messages;

import entities.ID;
import entities.Params;
import entities.Task;

public class ProviderResponse {

    ID serviceProviderId;
    private Task servedTask;
    private Params serviceParameters;

    public ProviderResponse(ID id, Task t, Params par) {
        serviceProviderId = id;
        servedTask = t;
        serviceParameters = par;
    }

    public Params getServiceParameters() {
        return serviceParameters;
    }

    public ID getServiceProviderId() {
        return serviceProviderId;
    }

    public Task getServedTask() {
        return servedTask;
    }

}

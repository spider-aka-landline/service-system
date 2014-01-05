package entities.providers;

import entities.Params;
import entities.Task;
import messages.ProviderResponse;

public class ServiceProvider {

    Params properties;
    //Double serviceQuality;

    public ServiceProvider(Params pr) {
        properties = pr;
    }

    public ProviderResponse processUserTask(Task t) {
        return new ProviderResponse(properties);
    }
}

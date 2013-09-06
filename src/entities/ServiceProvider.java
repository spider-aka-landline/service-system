package entities;

import messages.ProviderResponse;

public class ServiceProvider {

    
    public ServiceProvider() {
    }

    public ProviderResponse processUserTask(Task userTask) {
        return new ProviderResponse(true);
    }

}

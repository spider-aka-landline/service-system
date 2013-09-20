package entities;

import messages.ProviderResponse;

public class ServiceProvider implements Entity {

    Double serviceQuality;
    
    public ServiceProvider(Double sq) {
        serviceQuality = sq;
    }

    public ProviderResponse processUserTask(Task userTask) {
        return new ProviderResponse(new Params(serviceQuality));
    }

    @Override
    public void printProperty() {
        System.out.format("%.3f ",serviceQuality);
    }

}

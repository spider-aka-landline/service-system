package entities;

import messages.ProviderResponse;

public class ServiceProvider implements Entity {

    Double ServiceQuality;
    
    public ServiceProvider(Double sq) {
        ServiceQuality = sq;
    }

    public ProviderResponse processUserTask(Task userTask) {
        return new ProviderResponse(new Params(ServiceQuality));
    }

    @Override
    public void printProperty() {
        System.out.format("%.3f ",ServiceQuality);
    }

}

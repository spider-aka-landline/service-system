package messages;

import entities.Params;

public class ProviderResponse {

    Params serviceParameters;

    public ProviderResponse(Params par) {
        serviceParameters = par;
    }

    public Params getServiceParameters() {
        return serviceParameters;
    }
}

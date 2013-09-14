package messages;

import entities.Params;

public class ProviderResponse {
    
    Params service_parameters;
    
    public ProviderResponse(Params par){
        service_parameters = par;
    }
    
    public Params getResponseParameters(){
        return service_parameters;
    }
}

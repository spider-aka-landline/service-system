package messages;

public class ProviderResponse {
    
    //TaskType - enum //FIXME
    //Smth like result;
    //Smth like time of service;
    private final Boolean ServiceQualityStub; //FIXME: Next step
    
    public ProviderResponse(Boolean serviceQuality){
        ServiceQualityStub = serviceQuality;
    }
    
    //FIXME: Stub
    public Boolean getServiceQuality(){
        return ServiceQualityStub;
    }
}

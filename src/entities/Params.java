package entities;

public class Params {

    private final Double serviceQuality;
    
    Params(Double sq){
        serviceQuality = sq;
    }
    
    public Double getServiceQuality(){
        return serviceQuality;
    }
    
}

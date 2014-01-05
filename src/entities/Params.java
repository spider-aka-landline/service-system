package entities;

public class Params {

    private final Double serviceQuality;
    
    public Params(Double sq){
        serviceQuality = sq;
    }
    
    public Double getServiceQuality(){
        return serviceQuality;
    }
    
}

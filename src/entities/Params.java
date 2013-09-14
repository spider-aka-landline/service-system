package entities;

public class Params {

    private final Double ServiceQuality;
    
    Params(Double sq){
        ServiceQuality = sq;
    }
    
    public Double getServiceQuality(){
        return ServiceQuality;
    }
    
}

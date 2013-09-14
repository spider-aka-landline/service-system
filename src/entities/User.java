package entities;

import messages.UserResponse;
import messages.ProviderResponse;
import util.IO;

public class User implements Entity{

    private final Double needed_value; //Value, which is needed by user
    private final Double needed_quality; //One of the requirements
    
    public User(Double quality_level){
        needed_quality = quality_level;
        //хитрая функция от других параметров
        //ну, пока что не очень хитрая...
        needed_value = needed_quality;
    }
    
    public UserResponse generateResponse(ProviderResponse pR) {
        Double true_value = countValueFromParamsFunction(pR.getResponseParameters());
        Boolean diff = ((true_value - needed_value) >= 0);
        IO.log(true_value);
        return new UserResponse(true_value, diff);
    }

    //TODO: Fix params list
    private Double countEstimateFunction(Double quality) {
        return quality;
    }
    
    //TODO: Fix params list
    private Double countValueFromParamsFunction(Params p){
        return countEstimateFunction(p.getServiceQuality());
    }

    @Override
    public void printProperty() {
        System.out.format("%.3f ",needed_quality);
    }
}

package entities;

import messages.UserResponse;
import messages.ProviderResponse;

public class User {

    private final Double needed_value;

    public User(){
        this(0.0);
    }
    
    public User(Double treshold){
        needed_value = treshold;
    }
    public UserResponse generateResponse(ProviderResponse pR) {
        Double true_value = countEstimate(pR);
        Boolean diff = (true_value - needed_value) >= 0;
        return new UserResponse(true_value, diff);
    }

    //FIXME: Stub
    private Double countEstimate(ProviderResponse r) {
        int ans;
        if (r.getServiceQuality()) {
            ans = 1;
        } else {
            ans = -1;
        }
        return new Double(ans);
    }
}

package entities.users;

import entities.Params;
import messages.UserResponse;
import messages.ProviderResponse;
import util.IO;

public class User {

    private final Estimator estimator = new Estimator();
    private final Params requirements;

    public User(Params required) {
        requirements = required;
    }

    public UserResponse generateResponse(ProviderResponse pr) {
        Double rvalue = estimator.evaluateEstimate(requirements);
        Double value = estimator.evaluateEstimate(pr.getServiceParameters());
        Boolean diff = estimator.isPositiveDifference(value, rvalue);
        IO.log(value);
        return new UserResponse(value, diff);
    }

    @Override
    public String toString() {
        return requirements.toString();
    }
}

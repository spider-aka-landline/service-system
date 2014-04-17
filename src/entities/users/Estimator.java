package entities.users;

import entities.Params;

public class Estimator {

    /**
     * evaluates value function of the input parameters
     *
     * @param requirements
     * @return
     */
    public Double evaluateEstimate(Params requirements) {
        return requirements.getServiceQuality();
    }

    public Boolean isPositiveDifference(Double a, Double b) {
        return (a - b) >= 0;
    }

}

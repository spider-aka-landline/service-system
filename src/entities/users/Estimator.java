package entities.users;

import entities.Params;

public class Estimator {
    
    /** evaluates value function of the input parameters
     * @param requirements 
     * @return  */ 
    public Double evaluateEstimate(Params requirements) {
        return requirements.getServiceQuality();
    }
    
    public Boolean isPositiveDifference(Double a, Double b) {
     return isPositive(getDifference(a,b));
    }
    
    private Boolean isPositive(Double a) {
     return Boolean.valueOf(a.doubleValue() >= 0);
    }
    
    private Double getDifference(Double a, Double b) {
     return Double.valueOf(a.doubleValue() - b.doubleValue());
    }

}

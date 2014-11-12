package validator;

import comparators.MyNumberComparator;

public class SimpleValidator implements DifferenceValidator {

    public final static double VALIDATOR_INIT = 0.2;
    
    double epsilon;
    boolean inGap;

    public SimpleValidator(Number n) {
        epsilon = n.doubleValue();
        inGap = false;
    }

    public SimpleValidator() {
        epsilon = VALIDATOR_INIT;
        inGap = false;
    }

    @Override
    public Boolean isDifferenceInGap(Number delta) {
        //(new MyNumberComparator()).compare(delta, epsilon);
        return Math.abs(delta.doubleValue()) < epsilon;
    }

}

package validator;

import comparators.MyNumberComparator;

public class SimpleValidator implements DifferenceValidator {

    double epsilon;
    boolean inGap;

    public SimpleValidator(Number n) {
        epsilon = n.doubleValue();
        inGap = false;
    }

    @Override
    public Boolean isDifferenceInGap(Number delta) {
        //(new MyNumberComparator()).compare(delta, epsilon);
        return Math.abs(delta.doubleValue()) < epsilon;
    }

}

package validator;

import comparators.MyNumberComparator;

public class SimpleValidator implements DifferenceValidator {

    Number epsilon;
    boolean inGap;

    public SimpleValidator(Number n) {
        epsilon = n;
        inGap = false;
    }

    @Override
    public Boolean isDifferenceInGap(Number delta) {
        return (new MyNumberComparator()).compare(delta, epsilon) < 0;
    }

    public void checkDifferenceInGap(Number delta) {
        inGap = (new MyNumberComparator()).compare(delta, epsilon) < 0;
    }

}

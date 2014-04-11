package validator;

import comparators.MyNumberComparator;

public class SimpleValidator implements DifferenceValidator {

    Number epsilon; 
    
    public SimpleValidator(Number n){
        epsilon = n;
    }
    
    @Override
    public Boolean isDifferenceInGap(Number delta) {
        return (new MyNumberComparator()).compare(delta, epsilon) < 0;
    }

}

package strategies.newpackage;

import strategies.newpackage.criterae.Criteria;
import strategies.newpackage.criterae.MaxValueCriteria;

public class ReputationStrategy extends RLReputationStrategy {

    @Override
    Criteria getRLCriteria() {
        return new MaxValueCriteria();
    }

}

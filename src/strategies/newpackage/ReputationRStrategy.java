package strategies.newpackage;

import strategies.newpackage.criterae.Criteria;
import strategies.newpackage.criterae.MaxReputationCriteria;

public class ReputationRStrategy extends RLReputationStrategy {

    @Override
    Criteria getRLCriteria() {
        return new MaxReputationCriteria();
    }

}

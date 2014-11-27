package strategies.newpackage;

import entities.providers.ServiceProvider;
import java.util.Map;
import reputationsystem.DataEntity;
import reputationsystem.ProvidersReputationMap;
import reputationsystem.ReputationModule;
import strategies.newpackage.criterae.Criteria;
import strategies.newpackage.criterae.MaxReputationCriteria;

public class ReputationRStrategy extends RLStrategy {

    @Override
    Map<ServiceProvider, DataEntity>
            getSearchSet(ProvidersReputationMap acceptableProviders) {
        return acceptableProviders.getReputableProvidersData(
                ReputationModule.REPUTATION_MIN_LEVEL);
    }

    @Override
    Criteria getRLCriteria() {
        return new MaxReputationCriteria();
    }

}

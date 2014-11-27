package strategies.newpackage;

import entities.providers.ServiceProvider;
import java.util.Map;
import reputationsystem.DataEntity;
import reputationsystem.ProvidersReputationMap;
import strategies.newpackage.criterae.Criteria;
import strategies.newpackage.criterae.MaxValueCriteria;

public class SimpleRLStrategy extends RLStrategy{

    @Override
    Map<ServiceProvider, DataEntity> getSearchSet(
            ProvidersReputationMap acceptableProviders) {
        return acceptableProviders.getAllProvidersData();
    }

    @Override
    Criteria getRLCriteria() {
        return new MaxValueCriteria();
    }

}

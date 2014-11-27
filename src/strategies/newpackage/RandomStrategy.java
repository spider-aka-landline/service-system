package strategies.newpackage;

import entities.providers.ServiceProvider;
import exploration.ExplorationStrategy;
import java.util.Map;
import reputationsystem.DataEntity;
import reputationsystem.ProvidersReputationMap;
import strategies.newpackage.criterae.Criteria;
import strategies.newpackage.criterae.RandomCriteria;

public class RandomStrategy extends AbstractStrategy{

    @Override
    Criteria getCriteria() {
        return new RandomCriteria();
    }

    @Override
    Map<ServiceProvider, DataEntity> 
        getSearchSet(ProvidersReputationMap acceptableProviders, ExplorationStrategy exploration) {
        return acceptableProviders.getAllProvidersData();
    }

}

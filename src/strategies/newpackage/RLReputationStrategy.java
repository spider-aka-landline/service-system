package strategies.newpackage;

import entities.providers.ServiceProvider;
import reputationsystem.DataEntity;
import reputationsystem.ProvidersReputationMap;
import reputationsystem.ReputationModule;

import java.util.Map;

/**
 * Created by Spider on 12.12.2014.
 */
public abstract class RLReputationStrategy extends RLStrategy {

    @Override
    Map<ServiceProvider, DataEntity> getSearchSet(
            ProvidersReputationMap acceptableProviders) {
        return acceptableProviders.getReputableProvidersData(
                ReputationModule.REPUTATION_MIN_LEVEL);
    }
}

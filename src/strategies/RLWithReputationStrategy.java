package strategies;

import entities.providers.ServiceProvider;
import java.util.Map;
import reputationsystem.DataEntity;
import reputationsystem.ProvidersReputationMap;
import static reputationsystem.ReputationModule.REPUTATION_MIN_LEVEL;

public class RLWithReputationStrategy extends RLStrategy {

    @Override
    public Map<ServiceProvider, DataEntity>
            selectProvidersSearchSet(ProvidersReputationMap map) {
        return map.getReputableProviders(REPUTATION_MIN_LEVEL);
    }

}

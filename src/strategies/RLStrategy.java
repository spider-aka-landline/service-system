package strategies;

import entities.Task;
import entities.providers.ServiceProvider;
import exploration.ExplorationStrategy;
import java.util.Map;
import math.StdRandom;
import reputationsystem.DataEntity;
import reputationsystem.ProvidersReputationMap;

public class RLStrategy extends AbstractStrategy {

    @Override
    public Map<ServiceProvider, DataEntity>
            selectProvidersSearchSet(ProvidersReputationMap map) {
        return map.getAllProviders();
    }

    @Override
    public ServiceProvider
            chooseProvider(Task t, ExplorationStrategy exploration,
                    Map<ServiceProvider, DataEntity> searchSet) {
        if (StdRandom.bernoulli(exploration.getEpsilon())) {
            exploration.updateEpsilon();
            return chooseProviderFromMap(searchSet);
        } else {
            return chooseProviderFromMap(searchSet);
        }
    }

    @Override
    public ServiceProvider
            chooseProviderFromMap(Map<ServiceProvider, DataEntity> reputable) {
        return reputableChoice.RANDOM.chooseProviderFromReputable(reputable);
    }

}

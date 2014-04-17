package strategies;

import entities.Task;
import entities.providers.ServiceProvider;
import exploration.ExplorationStrategy;
import java.util.Map;
import math.StdRandom;
import reputationsystem.ChooseFromReputableStrategy;
import reputationsystem.DataEntity;
import reputationsystem.ProvidersReputationMap;

public abstract class AbstractStrategy implements Strategy {

    ChooseFromReputableStrategy reputableChoice;

    AbstractStrategy() {
        this(ChooseFromReputableStrategy.RANDOM);
    }

    AbstractStrategy(ChooseFromReputableStrategy s) {
        reputableChoice = s;
    }

    @Override
    public ServiceProvider chooseProviderForTask(Task t,
            ExplorationStrategy exploration,
            ProvidersReputationMap map) {
        Map<ServiceProvider, DataEntity> searchSet
                = selectProvidersSearchSet(map);

        return chooseProvider(t, exploration, searchSet);
    }

    public abstract Map<ServiceProvider, DataEntity>
            selectProvidersSearchSet(ProvidersReputationMap map);

    public abstract ServiceProvider
            chooseProvider(Task t, ExplorationStrategy exploration,
                    Map<ServiceProvider, DataEntity> searchSet);

    public ServiceProvider chooseProviderFromMap(
            Map<ServiceProvider, DataEntity> map) {
        if (map.isEmpty()) {
            return null;
        }
        return (ServiceProvider) map.keySet().toArray()[StdRandom.uniform(map.size())];
    }
}

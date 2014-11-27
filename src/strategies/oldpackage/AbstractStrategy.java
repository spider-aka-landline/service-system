package strategies.oldpackage;

import entities.Task;
import entities.providers.ServiceProvider;
import exploration.ExplorationStrategy;
import java.util.Map;
import math.StdRandom;
import reputationsystem.DataEntity;
import reputationsystem.ProvidersReputationMap;
import strategies.Strategy;

public abstract class AbstractStrategy implements Strategy {

    ChooseFromReputableStrategy reputableChoice;

    AbstractStrategy() {
        this(ChooseFromReputableStrategy.RANDOM);
    }

    AbstractStrategy(ChooseFromReputableStrategy s) {
        reputableChoice = s;
    }

   
    @Override
    public ServiceProvider chooseProvider(Task t,
            ExplorationStrategy exploration,
            ProvidersReputationMap map) {
        Map<ServiceProvider, DataEntity> searchSet
                = selectAcceptableProvidersSearchSet(t, map);

        return chooseProvider(exploration, searchSet, map);
    }

    public Map<ServiceProvider, DataEntity>
            selectAcceptableProvidersSearchSet(Task t, ProvidersReputationMap map) {
        return map.getAllProvidersData();
    }

    public abstract ServiceProvider
            chooseProvider(ExplorationStrategy exploration,
                    Map<ServiceProvider, DataEntity> searchSet,
                    ProvidersReputationMap map);

    /* by default - get random element */
    public ServiceProvider
            chooseProviderFromMapDefault(Map<ServiceProvider, DataEntity> map) {
        if (map.isEmpty()) {
            return null;
        }
        int randomIndex = StdRandom.uniform(map.size());
        return (ServiceProvider) map.keySet().toArray()[randomIndex];
    }


}

package strategies.oldpackage;

import entities.Task;
import entities.providers.ServiceProvider;
import exploration.ExplorationStrategy;
import java.util.Map;
import math.StdRandom;
import reputationsystem.DataEntity;
import reputationsystem.ProvidersReputationMap;

public class RandomStrategy extends AbstractStrategy {


    @Override
    public ServiceProvider
            chooseProvider(ExplorationStrategy exploration,
                    Map<ServiceProvider, DataEntity> searchSet,
                    ProvidersReputationMap map) {
        return chooseProviderFromMapDefault(searchSet);
    }

}

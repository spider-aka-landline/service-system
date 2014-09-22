package strategies;

import comparators.ExpectationComparator;
import entities.providers.ServiceProvider;
import exploration.ExplorationStrategy;
import java.util.Map;
import math.StdRandom;
import myutil.UtilFunctions;
import reputationsystem.DataEntity;
import reputationsystem.ProvidersReputationMap;

public class RLStrategy extends AbstractStrategy {

    @Override
    public ServiceProvider
            chooseProvider(ExplorationStrategy exploration,
                    Map<ServiceProvider, DataEntity> searchSet,
                    ProvidersReputationMap map) {
        if (StdRandom.bernoulli(exploration.getEpsilon())) {
            exploration.updateEpsilon();
            return chooseProviderFromMapDefault(searchSet);
        } else {
            exploration.updateEpsilon(); //TODO: should it be here?
            return exploit(map);
        }

    }

    protected ServiceProvider
            exploit(ProvidersReputationMap map) {
        return chooseProviderFromMapLogic(map.getAllProviders());
    }

    /*
     @Override
     public ServiceProvider
     chooseProviderFromMapDefault(Map<ServiceProvider, DataEntity> reputable) {
     return reputableChoice.RANDOM.chooseProviderFromReputable(reputable);
     }
     */
    protected ServiceProvider
            chooseProviderFromMapLogic(Map<ServiceProvider, DataEntity> searchSet) {
        return chooseProviderFromMapMaxValue(searchSet);
    }

    //TODO: rename method
        /* Вернуть провайдера c макс. ожиданием из авторитетных */
    protected ServiceProvider
            chooseProviderFromMapMaxValue(Map<ServiceProvider, DataEntity> search) {

        //найти максимальное значение ожидаемой ценности в множестве поиска
        Double max
                = UtilFunctions.getMaxValue(search, new ExpectationComparator()).getExpectation();
        //выбрать среди множества поиска провайдеров с максимальной ожидаемой ценностью
        Map<ServiceProvider, DataEntity> ReputableProvidersSet
                = UtilFunctions.filterMapByPredicate(
                        search, e -> e.getValue().getExpectation() == max);

        return chooseProviderFromMapDefault(ReputableProvidersSet);
    }
}

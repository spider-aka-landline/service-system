package strategies.oldpackage;

import comparators.ReputationComparator;
import entities.providers.ServiceProvider;
import java.util.Map;
import java.util.Set;
import io.UtilFunctions;
import reputationsystem.DataEntity;
import reputationsystem.ProvidersReputationMap;
import static reputationsystem.ReputationModule.REPUTATION_MIN_LEVEL;

public class RLWithReputationMaxReputationStrategy extends RLStrategy {

    private Map<ServiceProvider, DataEntity>
            selectReputableProvidersSearchSet(ProvidersReputationMap map) {
        Map<ServiceProvider, DataEntity> reputableProvidersSearchSet;
        reputableProvidersSearchSet
                = map.getReputableProvidersData(REPUTATION_MIN_LEVEL);
        if (reputableProvidersSearchSet.isEmpty()) {
         return map.getAllProvidersData();
         }
        else return reputableProvidersSearchSet;
    }

    @Override
    protected ServiceProvider
            exploit(ProvidersReputationMap map) {
        Map<ServiceProvider, DataEntity> reputableSearchSet
                = selectReputableProvidersSearchSet(map);

        return chooseProviderFromMapLogic(reputableSearchSet);
    }

    @Override
    protected ServiceProvider
            chooseProviderFromMapLogic(Map<ServiceProvider, DataEntity> searchSet) {
        return chooseProviderFromReputableWithMaxReputation(searchSet);
    }

    private ServiceProvider
            chooseProviderFromReputableWithMaxReputation(Map<ServiceProvider, DataEntity> reputable) {

        //поиск максимальной репутации
        Double max = UtilFunctions.getMaxValue(reputable,
                new ReputationComparator()).getReputation();

        //выбрать того, у кого максимальная репутация
        Set<ServiceProvider> choice
                = UtilFunctions.filterMapByPredicate(
                        reputable, e
                        -> e.getValue().getReputation().equals(max)).keySet();

        //Если не один - вернуть случайного
        return UtilFunctions.chooseRandomElement(choice);
    }
}

package strategies;

import entities.providers.ServiceProvider;
import java.util.Map;
import reputationsystem.DataEntity;
import reputationsystem.ProvidersReputationMap;
import static reputationsystem.ReputationModule.REPUTATION_MIN_LEVEL;

public class RLWithReputationStrategy extends RLStrategy {

    private Map<ServiceProvider, DataEntity>
            selectReputableProvidersSearchSet(ProvidersReputationMap map) {
        Map<ServiceProvider, DataEntity> resultSet;
        resultSet = map.getReputableProvidersData(REPUTATION_MIN_LEVEL);
        if (resultSet.isEmpty()) {
            return map.getAllProvidersData();
        } else return resultSet;
    }

    @Override
    protected ServiceProvider
            exploit(ProvidersReputationMap map) {
        Map<ServiceProvider, DataEntity> reputableSearchSet
                = selectReputableProvidersSearchSet(map);

        return chooseProviderFromMapLogic(reputableSearchSet);
    }

}

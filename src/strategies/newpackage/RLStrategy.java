package strategies.newpackage;

import entities.providers.ServiceProvider;
import exploration.ExplorationStrategy;
import java.util.Map;
import math.StdRandom;
import reputationsystem.DataEntity;
import reputationsystem.ProvidersReputationMap;
import strategies.newpackage.criterae.Criteria;
import strategies.newpackage.criterae.RandomCriteria;

public abstract class RLStrategy extends AbstractStrategy {

    private Boolean randomCoin;

    final boolean throwCoin(ExplorationStrategy exploration) {
        Double currentEpsilon = exploration.getEpsilon();
        exploration.updateEpsilon();
        return StdRandom.bernoulli(currentEpsilon);
    }

    @Override
    final Map<ServiceProvider, DataEntity>
            getSearchSet(ProvidersReputationMap acceptableProviders,
                    ExplorationStrategy exploration) {
        randomCoin = throwCoin(exploration);

        if (randomCoin) {
            return acceptableProviders.getAllProvidersData();
        } else {
            return getSearchSet(acceptableProviders);
        }

    }

    abstract Map<ServiceProvider, DataEntity>
            getSearchSet(ProvidersReputationMap acceptableProviders);

    @Override
    final Criteria getCriteria() {
        if (randomCoin == null) {
            throw new IllegalArgumentException("Coin fails");
        }
        Criteria tempCriteria
                = (randomCoin)
                        ? new RandomCriteria()
                        : getRLCriteria();

        randomCoin = null;
        return tempCriteria;
    }

    abstract Criteria getRLCriteria();

}

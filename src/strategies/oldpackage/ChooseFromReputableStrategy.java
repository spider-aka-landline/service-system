/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package strategies.oldpackage;

import comparators.ExpectationComparator;
import comparators.ReputationComparator;
import entities.Task;
import entities.providers.ServiceProvider;
import java.util.Map;
import java.util.Set;
import myutil.UtilFunctions;
import reputationsystem.DataEntity;
import strategies.oldpackage.RandomStrategy;

/**
 *
 * @author Spider
 */
public enum ChooseFromReputableStrategy {

    RANDOM {

                @Override
                public ServiceProvider chooseProviderFromReputable(
                        Map<ServiceProvider, DataEntity> reputable) {
                            return (new RandomStrategy()).chooseProviderFromMapDefault(reputable);
                        }

            },
    MAX_REPUTATION {

                @Override
                public ServiceProvider chooseProviderFromReputable(Map<ServiceProvider, DataEntity> reputable) {
                    Double max = UtilFunctions.getMaxValue(reputable,
                            new ReputationComparator()).getReputation();

                    //и среди найденных еще и выбрать того, у кого максимальная репутация
                    Set<ServiceProvider> choice
                    = UtilFunctions.filterMapByPredicate(
                            reputable, e
                            -> e.getValue().getReputation().equals(max)).keySet();

                    //Если не один - вернуть первого
                    return UtilFunctions.chooseRandomElement(choice);
                }

            };

    public ServiceProvider chooseProviderLogic(Task t,
            Map<ServiceProvider, DataEntity> searchMap) {

        //найти максимальное значение ожидаемой ценности в множестве поиска
        Double max
                = UtilFunctions.getMaxValue(searchMap, new ExpectationComparator()).getExpectation();
        //выбрать среди множества поиска провайдеров с максимальной ожидаемой ценностью
        Map<ServiceProvider, DataEntity> ReputableProvidersSet
                = UtilFunctions.filterMapByPredicate(
                        searchMap, e
                        -> e.getValue().getExpectation() == max);

        return chooseProviderFromReputable(ReputableProvidersSet);
    }

    public abstract ServiceProvider
            chooseProviderFromReputable(Map<ServiceProvider, DataEntity> reputable);

}

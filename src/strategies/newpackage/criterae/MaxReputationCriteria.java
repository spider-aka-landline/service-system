package strategies.newpackage.criterae;

import comparators.ReputationComparator;
import entities.providers.ServiceProvider;
import java.util.Map;
import java.util.Set;
import myutil.UtilFunctions;
import reputationsystem.DataEntity;

public class MaxReputationCriteria implements Criteria{

    @Override
    public ServiceProvider chooseProvider(
            Map<ServiceProvider, DataEntity> searchSet) {
        
        //поиск максимальной репутации
        Double max = UtilFunctions.getMaxValue(searchSet,
                new ReputationComparator()).getReputation();

        //выбрать того, у кого максимальная репутация
        Set<ServiceProvider> choice
                = UtilFunctions.filterMapByPredicate(
                        searchSet, e
                        -> e.getValue().getReputation().equals(max)).keySet();

        //Если не один - вернуть случайного
        return UtilFunctions.chooseRandomElement(choice);
        
    }

}
package strategies.newpackage.criterae;

import comparators.ExpectationComparator;
import entities.providers.ServiceProvider;
import java.util.Map;
import io.UtilFunctions;
import reputationsystem.DataEntity;

public class MaxValueCriteria implements Criteria {

    @Override
    public ServiceProvider chooseProvider(
            Map<ServiceProvider, DataEntity> searchSet) {

        //найти максимальное значение ожидаемой ценности в множестве поиска
        Double maxValue = UtilFunctions.getMaxValue(searchSet,
                new ExpectationComparator()).getExpectation();
        //выбрать из множества поиска провайдеров с максимальной ожидаемой ценностью
        Map<ServiceProvider, DataEntity> providersWithMaxValue
                = UtilFunctions.filterMapByPredicate(searchSet,
                        e -> e.getValue().getExpectation().equals(maxValue));

        return UtilFunctions.chooseRandomKey(providersWithMaxValue);
    }

}

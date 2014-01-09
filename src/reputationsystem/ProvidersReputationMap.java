package reputationsystem;

import entities.providers.ServiceProvider;
import java.util.HashMap;
import java.util.Map;
import util.UtilFunctions;

public class ProvidersReputationMap {

    public static final double EXPECTATION_INIT = 0;
    public static final double REPUTATION_INIT = 0;
    //exploration/exploitation strategy.
    final Map<ServiceProvider, DataEntity> serviceProviders = new HashMap<>();

    /* вернуть множество авторитетных провайдеров (репутация выше минимального порога для авторитетных) */
    Map getReputableProviders(double minlevel) {
        Map temp = UtilFunctions.filterMapByPredicate(serviceProviders,
                (Map.Entry<ServiceProvider, DataEntity> e)
                -> e.getValue().getReputation() > minlevel);
        if (temp.isEmpty()) return serviceProviders;
        return temp;
    }

    /* внешний вызов добавления нового провайдера */
    public void addServiceProvider(ServiceProvider sp) {
        if (!serviceProviders.containsKey(sp)) {
            serviceProviders.put(sp,
                    new DataEntity(REPUTATION_INIT, EXPECTATION_INIT));
        }
    }

    public void removeServiceProvider(ServiceProvider sp) {
        while (serviceProviders.containsKey(sp)) {
            serviceProviders.remove(sp);
        }
    }

    /* Обновление хранимых значений репутации и ожидаемых величин */
    public void update(ServiceProvider sp, Double reputation, Double expectation) {
        if (!serviceProviders.containsKey(sp)) {
            throw new IllegalArgumentException();
        }
        DataEntity entity = serviceProviders.get(sp);
        entity.setReputation(reputation);
        entity.setExpectation(expectation);
    }

    public DataEntity getServiceDataEntity(ServiceProvider sp) {
        return serviceProviders.get(sp);
    }

    public boolean isEmpty() {
        return serviceProviders.isEmpty();
    }

    ServiceProvider chooseRandomElement() {
        return UtilFunctions.chooseRandomElement(serviceProviders);
    }

}

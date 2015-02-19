package reputationsystem;

import comparators.QualityParamsComparator;
import entities.Params;
import entities.providers.ServiceProvider;
import io.UtilFunctions;

import java.util.*;

public class ProvidersReputationMap {

    public static final double EXPECTATION_INIT = 0;
    public static final double REPUTATION_INIT = 0;
    //exploration/exploitation strategy.

    private final Map<ServiceProvider, DataEntity> serviceProviders
            = new HashMap<>();

    /**
     * @param minlevel - минимальный порог репутации
     * @return множество авторитетных провайдеров (репутация выше минимального
     * порога для авторитетных)
     */
    public Map<ServiceProvider, DataEntity>
    getReputableProvidersData(double minlevel) {
        Map<ServiceProvider, DataEntity> temp
                = UtilFunctions.filterMapByPredicate(serviceProviders,
                e -> e.getValue().getReputation() > minlevel);
        return temp;
    }

    public Set<ServiceProvider> getReputableProviders(double minlevel) {
        return getReputableProvidersData(minlevel).keySet();

    }

    public ServiceProvider getWorstProvider() {
        return Collections.min(getAllProviders(),
                new QualityParamsComparator());
    }

    public ServiceProvider getBestProvider() {
        return Collections.max(getAllProviders(),
                new QualityParamsComparator());
    }

    public Map<ServiceProvider, DataEntity>
    getAllProvidersData() {
        return serviceProviders;
    }

    public Set<ServiceProvider>
    getAllProviders() {
        return serviceProviders.keySet();
    }

    /* внешний вызов добавления нового провайдера */
    public void addServiceProvider(ServiceProvider sp) {
        serviceProviders.putIfAbsent(sp,
                new DataEntity(REPUTATION_INIT, EXPECTATION_INIT));
    }

    public void addServiceProvider(Collection<ServiceProvider> providers) {
        providers.stream().forEach(this::addServiceProvider);
    }

    public DataEntity removeServiceProvider(ServiceProvider sp) {
        return serviceProviders.remove(sp);
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

    Boolean contains(ServiceProvider provider) {
        return serviceProviders.containsKey(provider);
    }

    public DataEntity getProviderData(ServiceProvider provider) {
        return serviceProviders.get(provider);
    }

    public void update(ServiceProvider changingProvider, Params changedParams) {
        if (changingProvider == null) {
            throw new NullPointerException("No provider to update");
        } else if (!serviceProviders.containsKey(changingProvider)) {
            throw new IllegalArgumentException(changingProvider.toString());
        }
        DataEntity entity = serviceProviders.get(changingProvider);
        serviceProviders.remove(changingProvider);

        //new provider. now is old with changing params
        changingProvider.setProperties(changedParams);

        serviceProviders.put(changingProvider, entity);
    }
}

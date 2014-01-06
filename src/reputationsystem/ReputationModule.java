package reputationsystem;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import math.StdRandom;
import util.UtilFunctions;

import entities.providers.ServiceProvider;
import entities.Task;
import exploration.ExplorationStrategy;

public class ReputationModule {

    //FIXME: indicates strategy. should be tramsformed into smth else.
    public static final Boolean CHOOSE_RANDOMLY_FROM_REPUTABLE = true;

    public static final double REPUTATION_INIT = 0;
    public static final double EXPECTATION_INIT = 0;
    public static final double REPUTATION_MIN_LEVEL = 0;

    public static final double COOPERATION_FACTOR = 0.4;
    public static final double NON_COOPERATION_FACTOR = -0.2;

    public final static double GAMMA_TD_INIT = 0.5;

    //exploration/exploitation strategy.
    private final Map<ServiceProvider, DataEntity> serviceProviders = new HashMap<>();
    private ExplorationStrategy exploration;
    private double gammaTd;

    public ReputationModule(Collection<ServiceProvider> pr,
            ExplorationStrategy str, Double g) {
        exploration = str;
        gammaTd = g;
        pr.forEach(sp -> {
            if (!serviceProviders.containsKey(sp)) {
                serviceProviders.put(sp,
                        new DataEntity(REPUTATION_INIT, EXPECTATION_INIT));
            }
        });
    }

    public ReputationModule(Collection<ServiceProvider> pr,
            ExplorationStrategy str) {
        this(pr, str, GAMMA_TD_INIT);
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
    public void update(ServiceProvider sp, Double estimate, Boolean isDifferencePositive) {
        if (!serviceProviders.containsKey(sp)) {
            throw new IllegalArgumentException();
        }
        DataEntity entity = serviceProviders.get(sp);
        //count new values for except and reputation
        Double reputation = updateReputation(entity.getReputation(), isDifferencePositive);
        Double expectation = updateExpectation(entity.getExpectation(), estimate);
        // update values in database
        entity.setReputation(reputation);
        entity.setExpectation(expectation);
    }

    /* Правило пересчета репутации провайдера */
    private Double updateReputation(Double old, Boolean differencePositive) {
        Double k = (differencePositive) ? COOPERATION_FACTOR : NON_COOPERATION_FACTOR;
        Double sign;
        if (old.equals(0.0)) {
            sign = 1.0;
        } else {
            sign = Math.signum(old);
        }

        Double result = old + k * (1 - sign * old);
        return result;
    }

    /**
     * TD-обучение
     *
     * @return new expectation
     */
    private Double updateExpectation(Double old, Double estimate) {
        Double delta = estimate - old;
        Double temp = old + gammaTd * delta;
        return temp;
    }

    /**
     * epsilon-decreasing стратегия выбора провайдера
     *
     * @param t User task
     * @return ServiceProvider, chosen for the task
     */
    public ServiceProvider chooseProvider(Task t) {
        if (serviceProviders.isEmpty()) {
            throw new RuntimeException("No service providers were found. Can't serve request.");
        }
        if (StdRandom.bernoulli(exploration.getEpsilon())) {
            exploration.updateEpsilon();
            return chooseProviderRandom(t);
        } else {
            return chooseProviderLogic(t);
        }
    }

    /* Выбор провайдера случайным образом */
    private ServiceProvider chooseProviderRandom(Task t) {
        return UtilFunctions.chooseRandomElement(serviceProviders);
    }

    /* вернуть множество авторитетных провайдеров (репутация выше минимального порога для авторитетных) */
    private Map<ServiceProvider, DataEntity> getReputableProviders() {
        return UtilFunctions.filterMapByPredicate(
                serviceProviders, e
                -> e.getValue().getReputation() > REPUTATION_MIN_LEVEL);
    }

    /* Выбор множества провайдеров, по которому ищем */
    private Map<ServiceProvider, DataEntity> selectProvidersSearchSet() {

        // Выбрать авторитетных
        Map<ServiceProvider, DataEntity> reputableProviders
                = getReputableProviders();
        //когда множество авторитетных пусто, выбираем среди всех
        if (reputableProviders.isEmpty()) {
            reputableProviders = serviceProviders;
        }

        return reputableProviders;
    }

    //TODO: rename method
    /* Вернуть провайдера c макс. ожиданием из авторитетных */
    private ServiceProvider chooseProviderLogic(Task t) {

        Map<ServiceProvider, DataEntity> search = selectProvidersSearchSet();

        //найти максимальное значение ожидаемой ценности в множестве поиска
        Double max
                = UtilFunctions.getMaxValue(search, new ExpectationComparator()).getExpectation();
        //выбрать среди множества поиска провайдеров с максимальной ожидаемой ценностью
        Map<ServiceProvider, DataEntity> ReputableProvidersSet
                = UtilFunctions.filterMapByPredicate(
                        search, e
                        -> e.getValue().getExpectation() == max);

        return (ServiceProvider) chooseProviderFromReputable(ReputableProvidersSet);

    }

    private <K extends ServiceProvider, V extends DataEntity> K
            chooseProviderFromReputable(Map<K, V> reputable) {
        if (CHOOSE_RANDOMLY_FROM_REPUTABLE) {
            //TODO: почему похожие методы разнесены по классам?
            return UtilFunctions.chooseRandomElement(reputable);
        } else {
            return chooseProviderFromReputableWithMaxReputation(reputable);
        }
    }

    private <K extends ServiceProvider, V extends DataEntity> K
            chooseProviderFromReputableWithMaxReputation(Map<K, V> reputable) {

        //Comparator for DataEntity - rep
        Comparator<V> cmp
                = (x, y) -> (x.getReputation() < y.getReputation())
                ? -1
                : (x.getReputation() > y.getReputation())
                ? 1 : 0;

        Double max = UtilFunctions.getMaxValue(reputable, cmp).getReputation();

        //и среди найденных еще и выбрать того, у кого максимальная репутация
        Set<K> choice
                = UtilFunctions.filterMapByPredicate(
                        reputable, e
                        -> e.getValue().getReputation() == max).keySet();

        //Если не один - вернуть первого
        return (K) UtilFunctions.chooseRandomElement(choice);
    }

}

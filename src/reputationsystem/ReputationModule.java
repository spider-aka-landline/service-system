package reputationsystem;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;

import math.StdRandom;
import myutil.UtilFunctions;

import entities.providers.ServiceProvider;
import entities.Task;
import exploration.ExplorationStrategy;

public class ReputationModule {

    //FIXME: indicates strategy. should be tramsformed into smth else.
    public static final Boolean CHOOSE_RANDOMLY_FROM_REPUTABLE = true;
    public static final double REPUTATION_MIN_LEVEL = 0;

    public static final double COOPERATION_FACTOR = 0.4;
    public static final double NON_COOPERATION_FACTOR = -0.2;

    public final static double GAMMA_TD_INIT = 0.5;

    private final ProvidersReputationMap providersReputationMap
            = new ProvidersReputationMap();
    private ExplorationStrategy exploration;
    private double gammaTd;

    public ReputationModule(Collection<ServiceProvider> pr,
            ExplorationStrategy str, Double g) {
        exploration = str;
        gammaTd = g;
        pr.forEach(sp -> providersReputationMap.addServiceProvider(sp));
    }

    public ReputationModule(Collection<ServiceProvider> pr,
            ExplorationStrategy str) {
        this(pr, str, GAMMA_TD_INIT);
    }

    /**
     * epsilon-decreasing стратегия выбора провайдера
     *
     * @param t User task
     * @param str strategy for choosing provider
     * @return ServiceProvider, chosen for the task
     */
    public ServiceProvider chooseProvider(Task t, ChooseProviderStrategy str) {
        if (providersReputationMap.isEmpty()) {
            throw new RuntimeException("No service providers were found. Can't serve request.");
        }

        switch (str) {
            case RL:
            case RLWITHREPUTATION:
                if (StdRandom.bernoulli(exploration.getEpsilon())) {
                    exploration.updateEpsilon();
                    return chooseProviderRandom(t);
                } else {
                    return chooseProviderLogic(t, str);
                }
            case RANDOM:
                return chooseProviderRandom(t);
            default:
                throw new RuntimeException("Experiment strategy type doesn't exist");
        }
    }
    /* Выбор провайдера случайным образом */

    private ServiceProvider chooseProviderRandom(Task t) {
        //do smth
        return providersReputationMap.chooseRandomElement();
    }

    /* Выбор множества провайдеров, по которому ищем */
    private Map<ServiceProvider, DataEntity>
            selectProvidersSearchSet(ChooseProviderStrategy str) {
        switch (str) {
            case RL:
                return providersReputationMap.serviceProviders;
            case RLWITHREPUTATION:
                return providersReputationMap.getReputableProviders(REPUTATION_MIN_LEVEL);
            case RANDOM:
            default:
                throw new RuntimeException("Wrong experiment strategy type");
        }
    }

    //TODO: rename method
        /* Вернуть провайдера c макс. ожиданием из авторитетных */
    private ServiceProvider chooseProviderLogic(Task t,
            ChooseProviderStrategy str) {

        Map<ServiceProvider, DataEntity> search = selectProvidersSearchSet(str);

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

    private ServiceProvider
            chooseProviderFromReputable(Map<ServiceProvider, DataEntity> reputable) {
        if (CHOOSE_RANDOMLY_FROM_REPUTABLE) {
            //TODO: почему похожие методы разнесены по классам?
            return UtilFunctions.chooseRandomElement(reputable);
        } else {
            return chooseProviderFromReputableWithMaxReputation(reputable);
        }
    }

    private ServiceProvider
            chooseProviderFromReputableWithMaxReputation(Map<ServiceProvider, DataEntity> reputable) {

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

    /**
     * TD-обучение
     *
     * @return new expectation
     */
    private Double updateExpectation(Double old, Double estimate) {
        Double delta = estimate - old;
        Double temp = old + this.gammaTd * delta;
        return temp;
    }

    /* Правило пересчета репутации провайдера */
    private Double updateReputation(Double old, Boolean differencePositive) {
        Double k = (differencePositive) ? ReputationModule.COOPERATION_FACTOR : ReputationModule.NON_COOPERATION_FACTOR;
        Double sign;
        if (old.equals(0.0)) {
            sign = 1.0;
        } else {
            sign = Math.signum(old);
        }
        Double result = old + k * (1 - sign * old);
        return result;
    }

    /* Обновление хранимых значений репутации и ожидаемых величин */
    public void update(ServiceProvider sp, Double estimate, Boolean isDifferencePositive) {
        DataEntity entity = providersReputationMap.getServiceDataEntity(sp);
        Double reputation = updateReputation(entity.getReputation(), isDifferencePositive);
        Double expectation = updateExpectation(entity.getExpectation(), estimate);

        providersReputationMap.update(sp, reputation, expectation);
    }

    public void addServiceProvider(ServiceProvider sp) {
        providersReputationMap.addServiceProvider(sp);
    }
}

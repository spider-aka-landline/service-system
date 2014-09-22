package reputationsystem;

import entities.Task;
import entities.providers.ServiceProvider;
import exploration.ExplorationStrategy;
import java.util.Collection;
import strategies.Strategy;

public class ReputationModule {

    public static final double REPUTATION_MIN_LEVEL = 0.6;

    public static final double COOPERATION_FACTOR = 0.4;
    public static final double NON_COOPERATION_FACTOR = -0.2;

    public final static float LEARNING_TD_PARAM_INIT = 1;
    public final static float LEARNING_TD_PARAM_MIN = 0.2f;
    public final static float LEARNING_TD_PARAM_STEP = 0.05f;

    private final ProvidersReputationMap providersReputationMap
            = new ProvidersReputationMap();

    private final ExplorationStrategy exploration;
    private float learningTdParam;

    public ReputationModule(Collection<ServiceProvider> pr,
            ExplorationStrategy str) {
        //    pr.stream().forEach(providersReputationMap::addServiceProvider);
        providersReputationMap.addServiceProvider(pr);
        exploration = str;
        learningTdParam = LEARNING_TD_PARAM_INIT;
    }

    public ReputationModule(Collection<ServiceProvider> pr,
            ExplorationStrategy str, Float g) {
        //pr.forEach(sp -> providersReputationMap.addServiceProvider(sp));
        providersReputationMap.addServiceProvider(pr);
        exploration = str;
        learningTdParam = g;
    }

    /**
     * epsilon-decreasing стратегия выбора провайдера
     *
     * @param t User task
     * @param str strategy for choosing provider
     * @return ServiceProvider, chosen for the task
     */
    public ServiceProvider chooseProvider(Task t, Strategy str) {
        if (providersReputationMap.isEmpty()) {
            throw new RuntimeException("No service providers were found. Can't serve request.");
        }

        return str.chooseProviderForTask(t, exploration, providersReputationMap);

    }

    public ProvidersReputationMap getprovidersReputationMap() {
        return providersReputationMap;
    }

    /**
     * TD-обучение
     *
     * @return new expectation
     */
    private Double updateExpectation(Double old, Double estimate) {
        Double delta = estimate - old;
        Double temp = old + this.learningTdParam * delta;
        updateLearningTDParam();
        return temp;
    }

    private void updateLearningTDParam() {
        //float tmp = learningTdParam - LEARNING_TD_PARAM_MIN;
        
        if (learningTdParam > LEARNING_TD_PARAM_MIN) {
            learningTdParam += -LEARNING_TD_PARAM_STEP;
        }
        //System.out.println(learningTdParam);
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
    public Double update(ServiceProvider sp, Double estimate, Boolean isDifferencePositive) {
        DataEntity entity = providersReputationMap.getServiceDataEntity(sp);
        Double reputation = updateReputation(entity.getReputation(), isDifferencePositive);
        Double oldExpectation = entity.getExpectation();
        Double expectation = updateExpectation(oldExpectation, estimate);

        providersReputationMap.update(sp, reputation, expectation);
        return estimate - oldExpectation;
    }

    public void addServiceProvider(ServiceProvider sp) {
        providersReputationMap.addServiceProvider(sp);
    }

}

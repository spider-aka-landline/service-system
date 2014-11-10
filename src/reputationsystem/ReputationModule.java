package reputationsystem;

import entities.providers.ServiceProvider;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class ReputationModule {

    public static final double REPUTATION_MIN_LEVEL = 0.6;

    public static final double COOPERATION_FACTOR = 0.4;
    public static final double NON_COOPERATION_FACTOR = -0.2;

    public final static float LEARNING_TD_PARAM_INIT = 1;
    public final static float LEARNING_TD_PARAM_MIN = 0.2f;
    public final static float LEARNING_TD_PARAM_STEP = 0.05f;

    private final ProvidersReputationMap providersReputationMap
            = new ProvidersReputationMap();

    private float learningTdParam;

    public ReputationModule(Collection<ServiceProvider> pr) {
        //    pr.stream().forEach(providersReputationMap::addServiceProvider);
        providersReputationMap.addServiceProvider(pr);
        learningTdParam = LEARNING_TD_PARAM_INIT;
    }

    public ReputationModule(Collection<ServiceProvider> pr, Float g) {
        //pr.forEach(sp -> providersReputationMap.addServiceProvider(sp));
        providersReputationMap.addServiceProvider(pr);
        learningTdParam = g;
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
        Double k = (differencePositive)
                ? ReputationModule.COOPERATION_FACTOR
                : ReputationModule.NON_COOPERATION_FACTOR;
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

    public Boolean isInReputable(ServiceProvider addedProvider) {
        return getAllReputableProviders().contains(addedProvider);
    }

    public boolean contains(ServiceProvider provider) {
        return providersReputationMap.getAllProviders().contains(provider);
    }

    private Set<ServiceProvider> getAllReputableProviders() {
        return providersReputationMap
                .getReputableProviders(REPUTATION_MIN_LEVEL);
    }
}

package reputationsystem;

import util.UtilFunctions;
import util.StdRandom;
import java.util.Map;
import java.util.HashMap;
import java.util.Collection;
import entities.ServiceProvider;
import entities.Task;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;

public class ReputationModule {

    public static final Boolean chooseRandomlyFromReputable = true;
    public static final double initReputation = 0;
    public static final double initExpectation = 0;

    public static final double min_reputation = 0;

    public static final double cooperation_factor = 0.4;
    public static final double non_cooperation_factor = -0.2;

    Map<ServiceProvider, DataEntity> ServiceProviders;

    public double gamma_td;
    public double epsilon_explore;
    private final double delta_epsilon = 0.03;
    private final double epsilon_min = 0.1;

    public ReputationModule() {
        ServiceProviders = new HashMap<>();
        epsilon_explore = 1;
        gamma_td = 0.5;
    }

    public ReputationModule(Collection<ServiceProvider> pr_list) {
        this();
        pr_list.forEach(b -> addNewServiceProvider(b));
    }

    private void addNewServiceProvider(ServiceProvider sp) {
        if (!ServiceProviders.containsKey(sp)) {
            ServiceProviders.put(sp,
                    new DataEntity(initReputation, initExpectation));
        }
    }

    /* внешний вызов добавления нового провайдера */
    public void addServiceProvider(ServiceProvider sp) {
        addNewServiceProvider(sp);
    }

    public void removeServiceProvider(ServiceProvider sp) {
        if (ServiceProviders.containsKey(sp)) {
            ServiceProviders.remove(sp);
        }
    }

    /* Обновление хранимых значений репутации и ожидаемых величин */
    public void update(ServiceProvider sp, Double estimate, Boolean isDifferencePositive) {
        if (!ServiceProviders.containsKey(sp)) {
            throw new IllegalArgumentException();
        }
        DataEntity tempEntity = ServiceProviders.get(sp);
        //count new values for except and reputation
        Double new_reputation = reputation_update_rule(tempEntity.getReputation(), isDifferencePositive);
        Double new_expectation = expectation_update_rule(tempEntity.getExpectation(), estimate);
        // update values in database
        tempEntity.setReputation(new_reputation);
        tempEntity.setExpectation(new_expectation);
        ServiceProviders.remove(sp);
        ServiceProviders.put(sp, tempEntity);
    }

    /* Правило пересчета репутации провайдера */
    private Double reputation_update_rule(Double old_reputation, Boolean differencePositive) {
        Double k = (differencePositive) ? cooperation_factor : non_cooperation_factor;
        Double sign;
        if (old_reputation.equals(0.0)) {
            sign = 1.0;
        } else {
            sign = Math.signum(old_reputation);
        }

        Double result;
        result = old_reputation + k * (1 + sign * old_reputation);
        return result;
    }

    /* TD-обучение returns new expectation */
    private Double expectation_update_rule(Double old_expectation, Double estimate) {
        Double delta = estimate - old_expectation;
        Double new_expectation = old_expectation + gamma_td * delta;
        return new_expectation;
    }

    /* epsilon-decreasing стратегия выбора провайдера */
    public ServiceProvider chooseProvider(Task t) {
        if (ServiceProviders.isEmpty()) throw new RuntimeException("No service providers were found. Can't serve request.");
        if (StdRandom.bernoulli(epsilon_explore)) {
            update_epsilon();
            return chooseProviderRandom(t);
        } else {
            return chooseProviderLogic(t);
        }
    }

    /* epsilon-decreasing strategy */
    private void update_epsilon() {
        if (epsilon_explore >= epsilon_min) {
            epsilon_explore -= delta_epsilon;
        }
    }

    /* Выбор провайдера случайным образом */
    private ServiceProvider chooseProviderRandom(Task t) {
        return UtilFunctions.chooseRandomElement(ServiceProviders);
    }
    
    /* вернуть множество авторитетных провайдеров (репутация выше минимального порога для авторитетных) */
    private Map<ServiceProvider, DataEntity> getReputableProviders() {
        return UtilFunctions.mapFilterPredicate(
                ServiceProviders, e
                -> e.getValue().getReputation() > min_reputation);
    }

    /* Выбор множества провайдеров, по которому ищем */
    private Map<ServiceProvider, DataEntity> selectProvidersSearchSet() {

        // Выбрать авторитетных
        Map<ServiceProvider, DataEntity> reputableProviders
                = getReputableProviders();
        //когда множество авторитетных пусто, выбираем среди всех
        if (reputableProviders.isEmpty()) {
            reputableProviders = ServiceProviders;
        }

        return reputableProviders;
    }

    /* Вернуть провайдера c макс. ожиданием из авторитетных */
    private ServiceProvider chooseProviderLogic(Task t) {

        Map<ServiceProvider, DataEntity> search_set = selectProvidersSearchSet();

        //Comparator for DataEntity - exp
        Comparator<DataEntity> exp_cmp
                = (x, y) -> (x.getExpectation() < y.getExpectation()) ? -1 
                : (x.getExpectation() > y.getExpectation()) ? 1 : 0;

        //найти максимальное значение ожидаемой ценности в множестве поиска
        double max_expectation
                = UtilFunctions.max_value(search_set, exp_cmp).getExpectation();
        //выбрать среди множества поиска провайдеров с максимальной ожидаемой ценностью
        Map<ServiceProvider, DataEntity> ReputableProvidersSet
                = UtilFunctions.mapFilterPredicate(
                search_set, e
                -> e.getValue().getExpectation() == max_expectation);

        return (ServiceProvider) chooseFromReputableSet(ReputableProvidersSet);

    }

    private <T extends ServiceProvider, V extends DataEntity> T chooseFromReputableSet(Map<T, V> best) {
            if (chooseRandomlyFromReputable) {
                return UtilFunctions.chooseRandomElement(best);
            } else {
                return chooseFromReputableSetMaxReputation(best);
            }
        }
    
    

    private <K extends ServiceProvider, V extends DataEntity> K chooseFromReputableSetMaxReputation(Map<K, V> best) {
        //Comparator for DataEntity - rep
        Comparator<V> rep_cmp
                = (x, y) -> (x.getReputation() < y.getReputation()) ? -1 : (x.getReputation() > y.getReputation()) ? 1 : 0;

        double max_reputation
                = UtilFunctions.max_value(best, rep_cmp).getReputation();

        //и среди найденных еще и выбрать того, у кого максимальная репутация
        Set<K> inner_best
                = UtilFunctions.mapFilterPredicate(
                best, e
                -> e.getValue().getReputation() == max_reputation).keySet();

        //Если не один - вернуть первого
        return (K) UtilFunctions.chooseRandomElement(inner_best);
    }
}

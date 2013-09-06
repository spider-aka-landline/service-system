package reputationsystem;

import util.UtilFunctions;
import java.util.Map;
import java.util.HashMap;
import java.util.Collection;
import entities.ServiceProvider;
import entities.Task;
import java.util.Collections;
import java.util.Comparator;
import java.util.Set;

public class ReputationModule {

    public static final double initReputation = 0;
    public static final double initExpectation = 0;

    public static final double min_reputation = 0;

    public static final double cooperation_factor = 0.4;
    public static final double non_cooperation_factor = -0.2;

    public double alpha_td;
    public double epsilon_explore;

    Map<ServiceProvider, DataEntity> ServiceProviders;

    public ReputationModule() {
        ServiceProviders = new HashMap<>();
        epsilon_explore = 1;
        alpha_td = 1;
    }

    public ReputationModule(Collection<ServiceProvider> pr_list) {
        this();
        pr_list.forEach(b -> {
            addNewServiceProvider((ServiceProvider) b);
        });
    }

    private void addNewServiceProvider(ServiceProvider sp) {
        if (!ServiceProviders.containsKey(sp)) {
            ServiceProviders.put(sp,
                    new DataEntity(initReputation, initExpectation));
        }
    }

    public void addServiceProvider(ServiceProvider sp) {
        addNewServiceProvider(sp);
    }

    public void removeServiceProvider(ServiceProvider sp) {
        if (ServiceProviders.containsKey(sp)) {
            ServiceProviders.remove(sp);
        }
    }

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

    /* returns new reputation */
    private Double reputation_update_rule(Double old_reputation, Boolean differencePositive) {
        Double k = new Double((differencePositive.booleanValue())
                ? cooperation_factor : non_cooperation_factor);
        Double sign;
        if (old_reputation.equals(0.0)) {
            sign = new Double(1.0);
        } else {
            sign = new Double(Math.signum(old_reputation));
        }

        Double result;
        result = new Double(old_reputation.doubleValue()
                + k.doubleValue()
                * (1 + sign.doubleValue() * old_reputation.doubleValue()));
        return result;
    }

    /* returns new expectation */
    private Double expectation_update_rule(Double old_expectation, Double estimate) {
        Double delta = new Double(estimate.doubleValue()
                - old_expectation.doubleValue());
        Double new_expectation = new Double(old_expectation.doubleValue()
                + alpha_td * delta.doubleValue());
        return new_expectation;
    }

   //вернуть авторитетных (репутация выше 0)
    private Map<ServiceProvider, DataEntity> getReputableProviders() {
        Map<ServiceProvider, DataEntity> ReputableProviders
                = UtilFunctions.mapFilterPredicate(
                ServiceProviders, e
                -> e.getValue().getReputation().doubleValue() > min_reputation);
        return ReputableProviders;
    }

    private DataEntity max_value(Map<ServiceProvider, DataEntity> map, Comparator<DataEntity> my_cmp) {
        return Collections.max(map.values(), my_cmp);
    }
    
    public ServiceProvider chooseProvider(Task t) {
        //FIXME: search with probability epsilon
        //if (StdRandom.bernoulli(epsilon_explore))
        //    return 
        //else 
            return chooseProviderLogic(t);
    }
    
    //Вернуть провайдера c макс. ожиданием из авторитетных
    public ServiceProvider chooseProviderLogic(Task t) {
        //выбрать авторитетных
        Map<ServiceProvider, DataEntity> reputableProviders
                = getReputableProviders();

        //Comparator for DataEntity - exp
        Comparator<DataEntity> exp_cmp
                = (x, y) -> (x.getExpectation() < y.getExpectation()) ? -1 : (x.getExpectation() > y.getExpectation()) ? 1 : 0;

        //найти максимальное значение ожидаемой ценности
        double max_expectation
                = max_value(reputableProviders,exp_cmp).getExpectation();
        //выбрать
        Map<ServiceProvider, DataEntity> bestProviders
                = UtilFunctions.mapFilterPredicate(
                getReputableProviders(), e
                -> e.getValue().getExpectation() == max_expectation);

        //(x)-> (x.getExpectation());
        //(x, y) -> (x < y) ? -1 : (x > y) ? 1 : 0;
                
        //Comparator for DataEntity - rep
        Comparator<DataEntity> rep_cmp
                = (x, y) -> (x.getReputation() < y.getReputation()) ? -1 : (x.getReputation() > y.getReputation()) ? 1 : 0;

        double max_reputation
                = max_value(reputableProviders,rep_cmp).getReputation();
        
        Set<ServiceProvider> best
                = UtilFunctions.mapFilterPredicate(
                bestProviders, e
                -> e.getValue().getReputation() == max_reputation).keySet();
        
        //g(f(x)) = g(y), y = f(x)
        //        x -> f(x)
        //                y -> g(y)
        
        return (ServiceProvider)best.toArray()[0];
    }

}

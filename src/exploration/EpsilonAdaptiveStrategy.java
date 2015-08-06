package exploration;

public class EpsilonAdaptiveStrategy extends ExplorationStrategy {

    public final static Double DEFAULT_EPSILON_MIN = 0.3;
    public final Double epsilonMin;

    public final Double beta;
    public final Double sigma;

    public EpsilonAdaptiveStrategy(Double epsilon, Double step, Double min, Double beta, Double sigma) {
        super(epsilon);
        epsilonMin = min;
        this.beta = beta;
        this.sigma = sigma;
    }

    public EpsilonAdaptiveStrategy() {
        super();
        epsilonMin = DEFAULT_EPSILON_MIN;
        beta = 1/30.;
        sigma = null; // FIXME
    }

    public void updateEpsilon(Double difference) {
        if (epsilon >= epsilonMin) {
            epsilon = epsilon*(1 - beta) + beta * getCurrentFunction();
        }
    }

    private Double getCurrentFunction() {
        //double d = - Math.abs(alpha*delta)/sigma; // FIXME
        return null;
    }
}

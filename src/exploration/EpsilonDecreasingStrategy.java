package exploration;

public class EpsilonDecreasingStrategy extends ExplorationStrategy {

    private final static Double DEFAULT_EPSILON_STEP = 0.03;
    public final static Double DEFAULT_EPSILON_MIN = 0.1;
    public final Double epsilonStep;
    public final Double epsilonMin;

    public EpsilonDecreasingStrategy(Double epsilon, Double step, Double min) {
        super(epsilon);
        epsilonStep = step;
        epsilonMin = min;
    }

    public EpsilonDecreasingStrategy() {
        super();
        epsilonStep = DEFAULT_EPSILON_STEP;
        epsilonMin = DEFAULT_EPSILON_MIN;
    }

    @Override
    public void updateEpsilon() {
        if (epsilon >= epsilonMin) {
            epsilon -= epsilonStep;
        }
    }
}
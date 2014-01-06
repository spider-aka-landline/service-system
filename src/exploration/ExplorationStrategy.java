package exploration;

public abstract class ExplorationStrategy {

    private final static Double DEFAULT_EPSILON_INIT = 1.0;
    protected Double epsilon;

    public ExplorationStrategy(Double einit) {
        epsilon = einit;
    }

    public ExplorationStrategy() {
        this(DEFAULT_EPSILON_INIT);
    }
    
    public Double getEpsilon(){
        return epsilon;
    }
    
    public abstract void updateEpsilon();
}

package entities.providers;

import entities.Params;
import entities.Task;
import math.StdRandom;
import messages.ProviderResponse;

public class RandomUniformServiceProvider extends ServiceProvider {

    Params propertiesLimits;

    /**
     *
     * @param i - provider ID
     * @param pr - service parameters
     * @param limits
     */
    public RandomUniformServiceProvider(int i, Params pr, Params limits) {
        super(i, pr);
        propertiesLimits = limits;
    }

    @Override
    public ProviderResponse processUserTask(Task t) {
        Params generatedProperties;
        generatedProperties = generateProperties();
        return new ProviderResponse(id, t, generatedProperties);
    }

    private Params generateProperties() {
        //FIXME
        Double sq = properties.getServiceQuality();
        Double sqLim = propertiesLimits.getServiceQuality();
        Double upper = sq + sqLim;
        Double lower = sq - sqLim;
        double generatedServiceQuality = StdRandom.uniform(lower, upper);
        if (generatedServiceQuality < 0) generatedServiceQuality = 0;

        Params tmp = new Params(generatedServiceQuality);
        return tmp;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder(String.valueOf(id));
        s.append(" ").append(properties);
        s.append(" ").append(propertiesLimits);
        return s.toString();
    }

}

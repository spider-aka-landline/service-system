package entities.generators;

import entities.Params;
import entities.providers.RandomUniformServiceProvider;
import entities.providers.ServiceProvider;

public class EntitiesGeneratorWithVariance extends EntitiesGenerator {

    private static EntitiesGeneratorWithVariance instance;

    Params paramsLimits;
  
    public static synchronized
            EntitiesGeneratorWithVariance getInstance(Params limits) {
        if (instance == null) {
            instance = new EntitiesGeneratorWithVariance();
            instance.paramsLimits = limits;
        }
        return instance;
    }

    @Override
    protected ServiceProvider createProvider(Params p) {
        ServiceProvider sp;
        sp = new RandomUniformServiceProvider(createID(providerCounter), p, paramsLimits);

        providerCounter++;
        return sp;
    }

}

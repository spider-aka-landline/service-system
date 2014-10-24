package myutil;

import entities.Params;
import entities.providers.RandomUniformServiceProvider;
import entities.providers.ServiceProvider;

public class GeneratorWithVariance extends Generator {

    Params paramsLimits;

    public GeneratorWithVariance(Params limits) {
        super();
        paramsLimits = limits;
    }

    
    @Override
    public ServiceProvider createProvider(Params p) {
        ServiceProvider sp;
        sp = new RandomUniformServiceProvider(providerCounter, p, paramsLimits);

        providerCounter++;
        return sp;
    }


}

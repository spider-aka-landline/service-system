package myutil.generators;

import entities.DipoleData;
import entities.Params;
import servicesystem.BruteForcerData;

public class BruteForcerDataGenerator {

    private static final Double FIXED_VARIANCE = 0.5;
    
    private static BruteForcerDataGenerator instance;

    private BruteForcerDataGenerator() {
    }

    public static synchronized BruteForcerDataGenerator getInstance() {
        if (instance == null) {
            instance = new BruteForcerDataGenerator();
        }
        return instance;
    }

    public BruteForcerData generateData(Boolean generateWithVariance, 
            DipoleData minimum, DipoleData maximum) {

        final Integer minUsersNumber = minimum.getUserNumber();
        final Integer maxUsersNumber = maximum.getUserNumber();

        final Integer minProvidersNumber = minimum.getProviderNumber();
        final Integer maxProvidersNumber = maximum.getProviderNumber();

        BruteForcerData data = new BruteForcerData();

        //get init data from the Gap Between the Worlds
        for (Integer u = minUsersNumber; u <= maxUsersNumber;
                u += logIncrement(u)) {
            for (Integer p = minProvidersNumber; p <= maxProvidersNumber;
                    p += logIncrement(p)) {
                data.addNumbers(new DipoleData(u, p));

                if (u.equals(minUsersNumber)) {
                    // for all providers
                    EntitiesGenerator gen;
                    if (generateWithVariance) {
                        Params variance = new Params(FIXED_VARIANCE);
                        gen = EntitiesGeneratorWithVariance.getInstance(variance);
                    } else {
                        gen = EntitiesGenerator.getInstance();
                    }
                    data.initProvidersBase(p, gen.generateProviders(p));
                }
            }
            // for all users
            data.putUsers(u, EntitiesGenerator.getInstance().generateUsers(u));
        }
        return data;
    }

    private static Integer logIncrement(Integer arg) {
        if (arg < 10) {
            return 1;
        } else {
            if (arg % 10 != 0) {
                throw new IllegalArgumentException("Wrong increment input");
            }
            return 10 * logIncrement(arg / 10);
        }
    }

}

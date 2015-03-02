package servicesystem.bruteforcer;

import entities.DipoleData;
import entities.Params;
import entities.generators.EntitiesGenerator;
import entities.generators.EntitiesGeneratorWithVariance;

import java.util.Collection;

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
                                        Collection<DipoleData> dipoles) {
        BruteForcerData data = new BruteForcerData();

        Integer users;
        Integer providers;
        for (DipoleData currentDipole : dipoles) {
            users = currentDipole.getUserNumber();
            providers = currentDipole.getProviderNumber();
            data.addNumbers(new DipoleData(users, providers));
            if (!data.containsUsersNumber(users)) {
                // for all users
                data.putUsers(users, EntitiesGenerator.getInstance().generateUsers(users));
            }

            if (!data.containsProvidersNumber(providers)) {
                // for all providers
                EntitiesGenerator gen;
                if (generateWithVariance) {
                    Params variance = new Params(FIXED_VARIANCE);
                    gen = EntitiesGeneratorWithVariance.getInstance(variance);
                } else {
                    gen = EntitiesGenerator.getInstance();
                }
                data.initProvidersBase(providers, gen.generateProviders(providers));
            }
        }

        return data;
    }

    public BruteForcerData readData() {
        return readDataWithoutVariance();
    }

    private BruteForcerData readDataWithoutVariance() {
/*
        final Integer minUsersNumber = minimum.getUserNumber();
        final Integer maxUsersNumber = maximum.getUserNumber();

        final Integer minProvidersNumber = minimum.getProviderNumber();
        final Integer maxProvidersNumber = maximum.getProviderNumber();
*/
        BruteForcerData data = new BruteForcerData();
/*
        data.initProvidersBase(p, gen.generateProviders(p));
        data.putUsers(u, EntitiesGenerator.getInstance().generateUsers(u));
  */
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

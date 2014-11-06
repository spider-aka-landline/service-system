/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package experiments;

import entities.Params;
import entities.providers.ServiceProvider;
import entities.users.User;
import java.util.Collection;
import myutil.generators.EntitiesGenerator;
import myutil.generators.EntitiesGeneratorWithVariance;

public class ExperimentDataWithVariance extends ExperimentData {

    /**
     *
     * @param usersc Users number
     * @param providersc Providers number
     * @param tasksc Tasks number
     * @param iterationsc total iterations number
     * @param parameters - variances vector
     */
    public ExperimentDataWithVariance(Integer usersc, Integer providersc, Integer tasksc,
            Integer iterationsc, Params parameters) {
        super(usersc, providersc, tasksc, iterationsc, false);

        EntitiesGenerator gen = EntitiesGeneratorWithVariance.getInstance(parameters);

        users.addAll(gen.generateUsers(usersNumber));
        providers.addAll(gen.generateProviders(providersNumber));
        tasks.addAll(gen.generateTasks(users, tasksNumber));

    }

    /**
     *
     * @param usersCollection collection of given users
     * @param providersCollection collection of given providers
     * @param tasksc Tasks number
     * @param iterationsc total iterations number
     */
    public ExperimentDataWithVariance(Collection<User> usersCollection,
            Collection<ServiceProvider> providersCollection,
            Integer tasksc, Integer iterationsc) {
        super(usersCollection, providersCollection, tasksc, iterationsc);
    }

}

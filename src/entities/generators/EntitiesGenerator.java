package entities.generators;

import entities.ID;
import entities.Params;
import entities.Task;
import entities.providers.ServiceProvider;
import entities.users.User;
import math.Poisson;
import math.StdRandom;
import io.UtilFunctions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class EntitiesGenerator {

    private static EntitiesGenerator instance;

    protected int maxProvidersNumber;

    protected long userCounter = 0;
    protected int providerCounter = 0;

    protected EntitiesGenerator() {
    }

    public static synchronized EntitiesGenerator getInstance() {
        if (instance == null) {
            instance = new EntitiesGenerator();
        }
        return instance;
    }

    public User createUser(Params p) {
        return new User(createID(userCounter++), p);
    }

    public User createUser() {
        return createUser(getRandomParams());
    }

    public ServiceProvider createStressProvider(Params p) {
        ServiceProvider sp = createProvider(p);
        resetProvidersNumberToMaxValue();
        return sp;
    }

    protected ServiceProvider createProvider(Params p) {
        ServiceProvider sp;
        sp = new ServiceProvider(createID(providerCounter), p);

        providerCounter++;
        return sp;
    }

    private ID createID(int param){
        return createID((long) param);
    }

    protected ID createID(long param){
        return new ID(param);
    }

    public ServiceProvider createStressProvider() {
        ServiceProvider sp = createProvider();
        resetProvidersNumberToMaxValue();
        return sp;
    }

    private ServiceProvider createProvider() {
        return createProvider(getRandomParams());
    }

    private Params getRandomParams() {
        return new Params(StdRandom.uniform(0, 1));
    }

    public Collection<User> generateUsers(Integer num) {
        Collection<User> tempU = new HashSet<>();
        for (int i = 0; i < num; i++) {
            tempU.add(createUser());
        }
        return tempU;
    }

    public Collection<ServiceProvider> generateProviders(Integer num) {
        maxProvidersNumber = num;
        Collection<ServiceProvider> tempP = new HashSet<>();
        for (int i = 0; i < num; i++) {
            tempP.add(createProvider());
        }
        return tempP;
    }

    public Collection<Task> generateTasks(Collection<User> users, Integer num) {
        Collection<Task> tempT = new ArrayList<>();
        Poisson numbers = new Poisson(42.0);
        for (int i = 0; i < num; i++) {
            tempT.add(new Task(UtilFunctions.chooseRandomElement(users),
                    numbers.next()));
        }
        return tempT;
    }


    public void resetProvidersNumberToMaxValue() {
        resetProvidersNumber(maxProvidersNumber);
    }

    private void resetProvidersNumber(int resetValue) {
        if (providerCounter > resetValue) {
            providerCounter = resetValue;
        }
    }

}

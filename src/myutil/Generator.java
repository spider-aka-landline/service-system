package myutil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import entities.Params;
import entities.Task;
import entities.providers.ServiceProvider;
import entities.users.User;
import math.Poisson;
import math.StdRandom;

public class Generator {

    private long userCounter;
    private int providerCounter;

    public User createUser(Params p) {
        return new User(++userCounter, p);
    }

    public User createUser() {
        return createUser(getRandomParams());
    }

    public ServiceProvider createProvider(Params p) {
        return new ServiceProvider(++providerCounter, p);
    }

    public ServiceProvider createProvider() {
        return createProvider(getRandomParams());
    }

    private Params getRandomParams() {
        return new Params(StdRandom.uniform(0, 1));
    }

    public Collection<User> generateUsers(Integer num) {
        Collection<User> tempU = new HashSet();
        for (int i = 0; i < num; i++) {
            tempU.add(createUser());
        }
        return tempU;
    }

    public Collection<ServiceProvider> generateProviders(Integer num) {
        Collection<ServiceProvider> tempP = new HashSet();
        for (int i = 0; i < num; i++) {
            tempP.add(createProvider());
        }
        return tempP;
    }

    public Collection<Task> generateTasks(Collection<User> users, Integer num) {
        Collection<Task> tempT = new ArrayList();
        Poisson numbers = new Poisson(42.0);
        for (int i = 0; i < num; i++) {
            tempT.add(new Task(UtilFunctions.chooseRandomElement(users),
                    numbers.next()));
        }
        return tempT;
    }

}
